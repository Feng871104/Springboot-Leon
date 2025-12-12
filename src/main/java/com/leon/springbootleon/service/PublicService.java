package com.leon.springbootleon.service;

import com.leon.springbootleon.utils.DateUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class PublicService {

    private final ObjectMapper objectMapper;

    public PublicService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public List<JsonNode> getMasterDegreeInfo() {
        var targetUri = "http://www.ck-exam.com.tw/emba06/emba_brochure/115/brochure115index.html";
        var doc = Jsoup.connect(targetUri)
                .userAgent("Mozilla")
                .timeout(100000)
                .get();
        Element tableEle = doc.getElementsByTag("table").get(1);
        Elements trs = tableEle.getElementsByTag("tr");
        ArrayNode arrayNode = objectMapper.createArrayNode();
        var condition = getJsonNodePredicate();
        trs.forEach(tr -> {
            Elements tds = tr.getElementsByTag("td");
            if (tds.size() >= 9 && !tds.get(0).text().isEmpty()) {
                Element downloadUriEle = tds.select("a[href]").first();
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("schoolName", tds.get(0).text());
                objectNode.put("brochureAnnounceDate", DateUtils.rocToIso(tds.get(1).text()));
                objectNode.put("applyStartDate", DateUtils.rocToIso(tds.get(2).text()));
                objectNode.put("applyEndDate", DateUtils.rocToIso(tds.get(3).text()));
                objectNode.put("writtenExamDate", DateUtils.rocToIso(tds.get(4).text()));
                objectNode.put("firstRoundReleaseDate", DateUtils.rocToIso(tds.get(5).text()));
                objectNode.put("interviewDate", DateUtils.rocToIso(tds.get(6).text()));
                objectNode.put("finalRoundReleaseDate", DateUtils.rocToIso(tds.get(7).text()));
                objectNode.put("notes", tds.get(8).text().isEmpty() ? "115簡章" : tds.get(8).text());
                objectNode.put("brochureDownloadLink", downloadUriEle != null ? "http://www.ck-exam.com.tw/emba06/emba_brochure/115/" + downloadUriEle.attr("href") : "");
                arrayNode.add(objectNode);
            }
        });
        return StreamSupport.stream(arrayNode.spliterator(), false)
                .filter(condition)
                .toList();
    }

    private static Predicate<JsonNode> getJsonNodePredicate() {
        Set<String> targetSchool = Set.of("台灣大學", "交通大學", "清華", "成功", "中央", "中興", "中正", "中山");
        Set<String> notSchool = Set.of("EMBA(復旦班)", "境外", "GMBA");
        Predicate<JsonNode> isHeader = i -> !"schoolName".equals(i.get("schoolName").asString());
        Predicate<JsonNode> isTargetSchool = i -> targetSchool.stream().anyMatch(i.get("schoolName").asString()::contains);
        Predicate<JsonNode> needSchool = i -> notSchool.stream().noneMatch(i.get("schoolName").asString()::contains);
        return isHeader
                .and(isTargetSchool)
                .and(needSchool);
    }
}
