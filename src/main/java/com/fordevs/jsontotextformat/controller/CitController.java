package com.fordevs.jsontotextformat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class CitController {

    @PostMapping(value = "/cit-mainframe-connector")
    public void citMainframeConnector(@RequestBody JsonNode jsonNode) {

        try {
            StringBuilder citRules = new StringBuilder();
            for (JsonNode citRule : jsonNode) {
                citRules = getFieldsAndValuesFromJsonNode(citRule);
            }
            System.out.println(citRules);
            bufferedWriter(citRules);

            //TODO:SFTP to MainFrame
            //TODO: After SFTP to Mainframe delete text report file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateOffsets(ListMultimap<String, String> multimap) {
        Integer offset = 0;
        Integer separatorWidth;
        Integer columnWidth;
        Integer totalBytes = 0;
        for (String key : multimap.keySet()) {
            multimap.put(key, String.valueOf(offset));
            columnWidth = Integer.parseInt(multimap.get(key).get(0));
            if (multimap.get(key).get(1).equals("")) {
                separatorWidth = 0;
            } else {
                separatorWidth = Integer.parseInt(multimap.get(key).get(1));
            }
            offset = columnWidth + separatorWidth + offset;
            totalBytes = totalBytes + offset;
        }
    }

    //get all field values per each cit rule
    public StringBuilder getFieldsAndValuesFromJsonNode(JsonNode jsonNode) throws IOException {

        LinkedListMultimap<String, String> fieldLayoutProperties = LinkedListMultimap.create();
        //ListMultimap<String, String> fieldLayoutProperties = ArrayListMultimap.create();
        //TODO:Make this configurable https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
        //TODO: Use MultiMapValue https://commons.apache.orag/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html
        fieldLayoutProperties.putAll("flowId", Arrays.asList("36", "1"));  //offset = 0
        fieldLayoutProperties.putAll("ptmRequestId", Arrays.asList("10", "1"));  //offset = 0 + 36 + 1 + 1 = 38
        fieldLayoutProperties.putAll("operId", Arrays.asList("8", "1"));   //offset 38 + 8 + 1 + 1 = 48  offset = previousOffset + fieldWidth + separatorWidth + 1
        fieldLayoutProperties.putAll("recordCount", Arrays.asList("6", "1"));
        fieldLayoutProperties.putAll("validFlagFile", Arrays.asList("1", "1"));
        fieldLayoutProperties.putAll("severityF", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("categoryF", Arrays.asList("14", "1"));
        fieldLayoutProperties.putAll("messageF", Arrays.asList("500", "1"));
        fieldLayoutProperties.putAll("userActionF", Arrays.asList("256", "1"));
        fieldLayoutProperties.putAll("procsStageF", Arrays.asList("24", "1"));
        fieldLayoutProperties.putAll("sourceF", Arrays.asList("6", "1"));
        fieldLayoutProperties.putAll("setUpChangeNumber", Arrays.asList("2", "1"));
        fieldLayoutProperties.putAll("rowNumber", Arrays.asList("10", "1"));
        fieldLayoutProperties.putAll("entityType", Arrays.asList("25", "1"));
        fieldLayoutProperties.putAll("entityOpId", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("entityAgn", Arrays.asList("25", "1"));
        fieldLayoutProperties.putAll("eastGrpOpId", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("srceEntity", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("srceEntityGr", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("srceCopay", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("entityEffDate", Arrays.asList("10", "1"));
        fieldLayoutProperties.putAll("channelCde", Arrays.asList("6", "1"));
        fieldLayoutProperties.putAll("parentType", Arrays.asList("2", "1"));
        fieldLayoutProperties.putAll("parentName", Arrays.asList("18", "1"));
        fieldLayoutProperties.putAll("parentAgn", Arrays.asList("10", "1"));
        fieldLayoutProperties.putAll("operation", Arrays.asList("1", "1"));
        fieldLayoutProperties.putAll("clientExclInd", Arrays.asList("1", "1"));
        fieldLayoutProperties.putAll("clientExcl", Arrays.asList("105", "")); //this is an array
//        fieldLayoutProperties.putAll("exclEntityTypCde", Arrays.asList("2", ""));
//        fieldLayoutProperties.putAll("exclEntity", Arrays.asList("18", ""));
//        fieldLayoutProperties.putAll("exclEntityAgn", Arrays.asList("10", ""));
//        fieldLayoutProperties.putAll("exclEastGroup", Arrays.asList("18", ""));
        fieldLayoutProperties.putAll("networkType", Arrays.asList("3", ""));
        fieldLayoutProperties.putAll("networkId", Arrays.asList("15", ""));
        fieldLayoutProperties.putAll("chain", Arrays.asList("5", ""));
        fieldLayoutProperties.putAll("chainName", Arrays.asList("35", ""));
        fieldLayoutProperties.putAll("npiNumber", Arrays.asList("10", ""));
        fieldLayoutProperties.putAll("pharName", Arrays.asList("35", ""));
        fieldLayoutProperties.putAll("state", Arrays.asList("2", ""));
        fieldLayoutProperties.putAll("networkExcl", Arrays.asList("360", "")); // array
//        fieldLayoutProperties.putAll("exclEntityTypCde", Arrays.asList("2", ""));
//        fieldLayoutProperties.putAll("exclEntity", Arrays.asList("18", ""));
//        fieldLayoutProperties.putAll("exclEntityAgn", Arrays.asList("10", ""));
//        fieldLayoutProperties.putAll("exclEastGroup", Arrays.asList("18", ""));
        fieldLayoutProperties.putAll("productType", Arrays.asList("20", ""));
        fieldLayoutProperties.putAll("productDetail", Arrays.asList("120", "")); //array
//        fieldLayoutProperties.putAll("productAttribute", Arrays.asList("20", ""));
//        fieldLayoutProperties.putAll("productValue", Arrays.asList("20", ""));
        fieldLayoutProperties.putAll("hierarchyType", Arrays.asList("1", ""));
        fieldLayoutProperties.putAll("intentEndDate", Arrays.asList("10", ""));
        fieldLayoutProperties.putAll("validFlagRec", Arrays.asList("1", ""));
        fieldLayoutProperties.putAll("severity", Arrays.asList("18", ""));
        fieldLayoutProperties.putAll("category", Arrays.asList("14", ""));
        fieldLayoutProperties.putAll("message", Arrays.asList("500", ""));
        fieldLayoutProperties.putAll("userAction", Arrays.asList("256", ""));
        fieldLayoutProperties.putAll("procsStage", Arrays.asList("24", ""));
        fieldLayoutProperties.putAll("source", Arrays.asList("6", ""));
        calculateOffsets(fieldLayoutProperties);

        LinkedListMultimap<String, String> arrayFieldLayoutProperties = LinkedListMultimap.create();
        //ListMultimap<String, String> arrayFieldLayoutProperties = ArrayListMultimap.create();
        arrayFieldLayoutProperties.putAll("exclEntityTypCde", Arrays.asList("2", ""));
        arrayFieldLayoutProperties.putAll("exclEntity", Arrays.asList("18", ""));
        arrayFieldLayoutProperties.putAll("exclEntityAgn", Arrays.asList("10", ""));
        arrayFieldLayoutProperties.putAll("exclEastGroup", Arrays.asList("18", ""));

        arrayFieldLayoutProperties.putAll("exclNetworkType", Arrays.asList("3", ""));
        arrayFieldLayoutProperties.putAll("exclNetworkId", Arrays.asList("15", ""));
        arrayFieldLayoutProperties.putAll("exclChain", Arrays.asList("5", ""));
        arrayFieldLayoutProperties.putAll("exclNpiNumber", Arrays.asList("10", ""));
        arrayFieldLayoutProperties.putAll("exclState", Arrays.asList("2", ""));

        arrayFieldLayoutProperties.putAll("productAttribute", Arrays.asList("20", ""));
        arrayFieldLayoutProperties.putAll("productValue", Arrays.asList("20", ""));

        StringBuilder ruleRow = new StringBuilder();
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();

            if (value.isArray()) {
                for (JsonNode arrayElement : value) {
                    Iterator<Map.Entry<String, JsonNode>> arrayFields = arrayElement.fields();
                    while (arrayFields.hasNext()) {
                        Map.Entry<String, JsonNode> arrayField = arrayFields.next();
                        String arrayKey = arrayField.getKey();
                        String arrayValue = arrayField.getValue().asText();
                        if (arrayFieldLayoutProperties.containsKey(arrayKey)) {
                            String columnWidth = arrayFieldLayoutProperties.get(arrayKey).get(0);
                            String columnGapWidth = arrayFieldLayoutProperties.get(arrayKey).get(1);
                            String ruleField = String.valueOf(formatRuleField(arrayKey, arrayValue, columnWidth, columnGapWidth));
                            ruleRow.append(ruleField);
                        }
                    }
                }
            } else {
                if (fieldLayoutProperties.containsKey(key)) {
                    String columnWidth = fieldLayoutProperties.get(key).get(0);
                    String columnGapWidth = fieldLayoutProperties.get(key).get(1);
                    Integer offset = Integer.valueOf(fieldLayoutProperties.get(key).get(2));
                    String ruleField = String.valueOf(formatRuleField(key, value.asText(), columnWidth, columnGapWidth));
                    ruleRow.insert(offset, ruleField);
                }
            }
        }
        ruleRow.append("\n");
        return ruleRow;
    }

    public StringBuilder formatRuleField(String key, String value, String keyColumnWidth, String keySeparatorWidth) {

        StringBuilder ruleField = new StringBuilder();
        //String.format specifiers: %[flags][width][.precision][argsize] typechar.
        ruleField.append(String.format("%" + keyColumnWidth + "s %" + keySeparatorWidth + "s", value, ""));
        return ruleField;
    }


    public void bufferedWriter(StringBuilder content) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/reports/report_.txt", true);
            //append string buffer/builder to buffered writer
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }
}

