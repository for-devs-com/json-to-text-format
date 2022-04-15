package com.fordevs.jsontotextformat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.LinkedListMultimap;
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

@RestController
@RequestMapping("/pharmacylevelbenefit/v1/partialintent")
@Slf4j
public class CitController {

    @PostMapping(value = "/cit-mainframe-connector")
    public void citMainframeConnector(@RequestBody JsonNode jsonNode) {

        try {
            //TODO:Make this configurable https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
            //TODO: Use MultiMapValue https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html
            StringBuilder citRules = new StringBuilder(5000);
            // LinkedListMultimap<String, String> keyValueMultiMap = LinkedListMultimap.create();


            for (JsonNode jsonNodeCitRule : jsonNode) {
                getKeyValueMultiMapFromJson(jsonNodeCitRule);
                // citRules.append();
            }
            System.out.println(citRules);
            bufferedWriter(citRules);

            //TODO:SFTP to MainFrame
            //TODO: After SFTP to Mainframe delete text report file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //get all field values per each cit rule
    public StringBuilder getKeyValueMultiMapFromJson(JsonNode jsonNode) throws IOException {

        LinkedListMultimap<String, String> fieldWidthMultiMap = LinkedListMultimap.create();
        //TODO:Make this configurable https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
        //TODO: Use MultiMapValue https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html

        fieldWidthMultiMap.putAll("flowId", Arrays.asList("36", "1"));  //offset = 0
        fieldWidthMultiMap.putAll("ptmRequestId", Arrays.asList("10", "1"));  //offset = 0 + 36 + 1 + 1 = 38
        fieldWidthMultiMap.putAll("operId", Arrays.asList("8", "1"));   //offset 38 + 8 + 1 + 1 = 38  offset = previousOffset + fieldWidth + separatorWidth + 1
        fieldWidthMultiMap.putAll("recordCount", Arrays.asList("6", "1"));
        fieldWidthMultiMap.putAll("validFlagFile", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("categoryF", Arrays.asList("14", "1"));
        fieldWidthMultiMap.putAll("messageF", Arrays.asList("500", "1"));
        fieldWidthMultiMap.putAll("userActionF", Arrays.asList("256", "1"));
        fieldWidthMultiMap.putAll("procsStageF", Arrays.asList("24", "1"));
        fieldWidthMultiMap.putAll("sourceF", Arrays.asList("6", "1"));
        fieldWidthMultiMap.putAll("setUpChangeNumber", Arrays.asList("2", "1"));
        fieldWidthMultiMap.putAll("rowNumber", Arrays.asList("10", "1"));
        fieldWidthMultiMap.putAll("entityType", Arrays.asList("25", "1"));
        fieldWidthMultiMap.putAll("entityOpId", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("entityAgn", Arrays.asList("25", "1"));
        fieldWidthMultiMap.putAll("eastGrpOpId", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("srceEntity", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("srceEntityGr", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("srceCopay", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("entityEffDate", Arrays.asList("10", "1"));
        fieldWidthMultiMap.putAll("channelCde", Arrays.asList("6", "1"));
        fieldWidthMultiMap.putAll("parentType", Arrays.asList("2", "1"));
        fieldWidthMultiMap.putAll("parentName", Arrays.asList("18", "1"));
        fieldWidthMultiMap.putAll("parentAgn", Arrays.asList("10", "1"));
        fieldWidthMultiMap.putAll("operation", Arrays.asList("1", "1"));
        fieldWidthMultiMap.putAll("clientExclInd", Arrays.asList("1", "1"));
        fieldWidthMultiMap.putAll("exclEntityTypCde", Arrays.asList("25", ""));
        fieldWidthMultiMap.putAll("exclEntity", Arrays.asList("18", ""));
        fieldWidthMultiMap.putAll("exclEntityAgn", Arrays.asList("10", ""));
        fieldWidthMultiMap.putAll("exclEastGroup", Arrays.asList("18", ""));
        fieldWidthMultiMap.putAll("networkType", Arrays.asList("3", ""));
        fieldWidthMultiMap.putAll("networkId", Arrays.asList("15", ""));
        fieldWidthMultiMap.putAll("chain", Arrays.asList("5", ""));
        fieldWidthMultiMap.putAll("chainName", Arrays.asList("35", ""));
        fieldWidthMultiMap.putAll("npiNumber", Arrays.asList("10", ""));
        fieldWidthMultiMap.putAll("pharName", Arrays.asList("35", ""));
        fieldWidthMultiMap.putAll("state", Arrays.asList("2", ""));
        fieldWidthMultiMap.putAll("networkExcl", Arrays.asList("38")); //offset = previousOffset + fieldWidth + separatorWidth + 1
        fieldWidthMultiMap.putAll("networkExclInd", Arrays.asList("1", ""));
        fieldWidthMultiMap.putAll("exclNetworkType", Arrays.asList("3", ""));
        fieldWidthMultiMap.putAll("exclNetworkId", Arrays.asList("15", ""));
        fieldWidthMultiMap.putAll("exclChain", Arrays.asList("5", ""));
        fieldWidthMultiMap.putAll("exclNpiNumber", Arrays.asList("10", ""));
        fieldWidthMultiMap.putAll("exclState", Arrays.asList("2", ""));
        fieldWidthMultiMap.putAll("productType", Arrays.asList("20", ""));
        fieldWidthMultiMap.putAll("productAttribute", Arrays.asList("20", ""));
        fieldWidthMultiMap.putAll("productValue", Arrays.asList("20", ""));
        fieldWidthMultiMap.putAll("hierarchyType", Arrays.asList("1", ""));
        fieldWidthMultiMap.putAll("intentEndDate", Arrays.asList("10", ""));
        fieldWidthMultiMap.putAll("validFlagRec", Arrays.asList("1", ""));
        fieldWidthMultiMap.putAll("severity", Arrays.asList("18", ""));
        fieldWidthMultiMap.putAll("category", Arrays.asList("14", ""));
        fieldWidthMultiMap.putAll("message", Arrays.asList("500", ""));
        fieldWidthMultiMap.putAll("userAction", Arrays.asList("256", ""));
        fieldWidthMultiMap.putAll("procsStage", Arrays.asList("24", ""));
        fieldWidthMultiMap.putAll("source", Arrays.asList("6", ""));

        StringBuilder citRuleRow = new StringBuilder();
        //LinkedListMultimap<String, String> multiMap = LinkedListMultimap.create();
        //ObjectMapper objectMapper = new ObjectMapper();
        //get all the fields in this JsonNode
        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            String fieldName;
            String fieldWidth = null;
            String separatorWidth = null;

            while (fieldNames.hasNext()) {

                fieldName = fieldNames.next();

                JsonNode jsonNodeValue = jsonNode.get(fieldName);
                if (jsonNodeValue.isArray()) {
                    //citRule.append(setBufferSize(fieldName));
//                    List<String> list = objectMapper.convertValue(jsonNodeValue, ArrayList.class);
//                    multiMap.putAll(fieldName, list);

                    int offset = 0;
                    if (fieldWidthMultiMap.containsKey(fieldName)) {
                        offset = Integer.parseInt(fieldWidthMultiMap.get(fieldName).get(0));
                    }

                    StringBuilder arrayValues = new StringBuilder();
                    for (JsonNode arrayNodeFieldNames : jsonNodeValue) {
                        Iterator<String> arrayFieldNames = arrayNodeFieldNames.fieldNames();
                        String arrayfieldName;
                        String arrayFieldValue;
                        while (arrayFieldNames.hasNext()) {
                            arrayfieldName = arrayFieldNames.next();
                            JsonNode arrayFieldValueNode = arrayNodeFieldNames.get(arrayfieldName);
                            arrayFieldValue = arrayFieldValueNode.asText();
                            log.info("fieldName  : {}", arrayfieldName);
                            log.info("fieldValue = {}", arrayFieldValue);
                            if (fieldWidthMultiMap.containsKey(arrayfieldName)) { //networkExl
                                fieldWidth = fieldWidthMultiMap.get(arrayfieldName).get(0);
                                separatorWidth = fieldWidthMultiMap.get(arrayfieldName).get(1);
                            }
                            arrayValues.append(formatFieldValue(arrayfieldName, arrayFieldValue, fieldWidth, separatorWidth));
                        }
                    }
                    citRuleRow.insert(offset, arrayValues);
                } else {
                    String fieldValue = jsonNodeValue.asText();

                    if (fieldWidthMultiMap.containsKey(fieldName)) { //networkExl
                         fieldWidth = fieldWidthMultiMap.get(fieldName).get(0);
                        separatorWidth = fieldWidthMultiMap.get(fieldName).get(1);
                    }
                    citRuleRow.append(formatFieldValue(fieldName, fieldValue, fieldWidth, separatorWidth));
                }
            }
            //citRule.append("\n");
        }
        //return citRule;
        return citRuleRow;
    }

    public StringBuilder formatFieldValue(String fieldName, String fieldValue, String fieldWidth, String separatorWidth) {

        //LinkedListMultimap<String, Integer> fieldWidthMultiMap = LinkedListMultimap.create();
//        LinkedHashMap<String, List<String>> fieldWidths = new LinkedHashMap<>();
//
//        fieldWidths.put("flowId", Arrays.asList("36", "1"));
        //TODO:Make this configurable https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
        //TODO: Use MultiMapValue https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html

        StringBuilder formatFieldValue = new StringBuilder();
        //String.format specifiers: %[flags][width][.precision][argsize] typechar.
        formatFieldValue.append(String.format("%" + fieldWidth + "s %" + separatorWidth + "s", fieldValue, ""));
        return formatFieldValue;
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

//    public StringBuilder setBufferSize(String key) {
//        Map<String, Integer> map = new HashMap<>();
//        map.put("networkExcl", 105);
//        map.put("clientExcl", 380);
//        map.put("productDetail", 120);
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        if (map.containsKey(key)) {
//            Integer bufferSize = map.get(key);
//            stringBuilder.setLength(bufferSize);
//        }
//
//        return stringBuilder;
//    }


//            if (fieldName.equals("flowId") ||
//                    fieldName.equals("ptmRequestId") ||
//                    fieldName.equals("operId") ||
//                    fieldName.equals("recordCount") ||
//                    fieldName.equals("validFlagFile") ||
//                    fieldName.equals("severityF") ||
//                    fieldName.equals("categoryF") ||
//                    fieldName.equals("messageF") ||
//                    fieldName.equals("userActionF") ||
//                    fieldName.equals("procsStageF") ||
//                    fieldName.equals("clientExclInd")) {
//                fillerWidth = "1";
//                formatFieldValue.append(String.format("%" + fieldWidth + "s %" + fillerWidth + "s", fieldValue, filler));
//            } else if (fieldName.equals("sourceF")) {
//                fillerWidth = "1252";
//                formatFieldValue.append(String.format("%" + fieldWidth + "s %" + fillerWidth + "s", fieldValue, filler));
//            } else {
//                formatFieldValue.append(String.format("%" + fieldWidth + "s", fieldValue));
//            }

//        for( Object key : fieldWidthMultiMap.keySet()){
//            log.info("key: " + key.toString() + );
//

//            int offset = fieldWidthMultiMap.get(key).get(0);
//            String fieldWidth = String.valueOf(fieldWidthMultiMap.get(key).get(1));
//            String separatorWidth = String.valueOf(fieldWidthMultiMap.get(key).get(2));
//            Integer bufferSize = Integer.valueOf(fieldWidth) + Integer.valueOf(separatorWidth);
//formatFieldValue.setLength(bufferSize);

//        fieldWidthMultiMap.putAll("flowId", Arrays.asList(0, 36, 1));
//                fieldWidthMultiMap.putAll("ptmRequestId", Arrays.asList(38, 10, 1));
//                fieldWidthMultiMap.putAll("operId", Arrays.asList(50, 8, 1));
//                fieldWidthMultiMap.putAll("recordCount", Arrays.asList(60, 6, 1));
//                fieldWidthMultiMap.putAll("validFlagFile", Arrays.asList(68, 18, 1));
//                fieldWidthMultiMap.putAll("categoryF", Arrays.asList(88, 14, 1));
//                fieldWidthMultiMap.putAll("messageF", Arrays.asList(104, 500, 1));
//                fieldWidthMultiMap.putAll("userActionF", Arrays.asList(606, 256, 1));
//                fieldWidthMultiMap.putAll("procsStageF", Arrays.asList(864, 24, 1));
//                fieldWidthMultiMap.putAll("sourceF", Arrays.asList(890, 6, 1));
//                fieldWidthMultiMap.putAll("setUpChangeNumber", Arrays.asList(2, 1));
//                fieldWidthMultiMap.putAll("rowNumber", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("entityType", Arrays.asList(25, 1));
//                fieldWidthMultiMap.putAll("entityOpId", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("entityAgn", Arrays.asList(25, 1));
//                fieldWidthMultiMap.putAll("eastGrpOpId", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceEntity", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceEntityGr", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceCopay", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("entityEffDate", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("channelCde", Arrays.asList(6, 1));
//                fieldWidthMultiMap.putAll("parentType", Arrays.asList(2, 1));
//                fieldWidthMultiMap.putAll("parentName", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("parentAgn", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("operation", Arrays.asList(1, 1));
//                fieldWidthMultiMap.putAll("clientExclInd", Arrays.asList(1, 1));
//                fieldWidthMultiMap.putAll("exclEntityTypCde", Arrays.asList(25, 0));
//                fieldWidthMultiMap.putAll("exclEntity", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("exclEntityAgn", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("exclEastGroup", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("networkType", Arrays.asList(3, 0));
//                fieldWidthMultiMap.putAll("networkId", Arrays.asList(15, 0));
//                fieldWidthMultiMap.putAll("chain", Arrays.asList(5, 0));
//                fieldWidthMultiMap.putAll("chainName", Arrays.asList(35, 0));
//                fieldWidthMultiMap.putAll("npiNumber", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("pharName", Arrays.asList(35, 0));
//                fieldWidthMultiMap.putAll("state", Arrays.asList(2, 0));
//                fieldWidthMultiMap.putAll("networkExcl", Arrays.asList(380, 0));
//                fieldWidthMultiMap.putAll("networkExclInd", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("exclNetworkType", Arrays.asList(3, 0));
//                fieldWidthMultiMap.putAll("exclNetworkId", Arrays.asList(15, 0));
//                fieldWidthMultiMap.putAll("exclChain", Arrays.asList(5, 0));
//                fieldWidthMultiMap.putAll("exclNpiNumber", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("exclState", Arrays.asList(2, 0));
//                fieldWidthMultiMap.putAll("productType", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("productAttribute", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("productValue", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("hierarchyType", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("intentEndDate", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("validFlagRec", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("severity", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("category", Arrays.asList(14, 0));
//                fieldWidthMultiMap.putAll("message", Arrays.asList(500, 0));
//                fieldWidthMultiMap.putAll("userAction", Arrays.asList(256, 0));
//                fieldWidthMultiMap.putAll("procsStage", Arrays.asList(24, 0));
//                fieldWidthMultiMap.putAll("source", Arrays.asList(6, 0));

//        fieldWidthMultiMap.putAll("flowId", Arrays.asList(36, 1));
//                fieldWidthMultiMap.putAll("ptmRequestId", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("operId", Arrays.asList(8, 1));
//                fieldWidthMultiMap.putAll("recordCount", Arrays.asList(6, 1));
//                fieldWidthMultiMap.putAll("validFlagFile", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("categoryF", Arrays.asList(14, 1));
//                fieldWidthMultiMap.putAll("messageF", Arrays.asList(500, 1));
//                fieldWidthMultiMap.putAll("userActionF", Arrays.asList(256, 1));
//                fieldWidthMultiMap.putAll("procsStageF", Arrays.asList(864, 24, 1));
//                fieldWidthMultiMap.putAll("sourceF", Arrays.asList(890, 6, 1));
//                fieldWidthMultiMap.putAll("setUpChangeNumber", Arrays.asList(2, 1));
//                fieldWidthMultiMap.putAll("rowNumber", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("entityType", Arrays.asList(25, 1));
//                fieldWidthMultiMap.putAll("entityOpId", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("entityAgn", Arrays.asList(25, 1));
//                fieldWidthMultiMap.putAll("eastGrpOpId", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceEntity", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceEntityGr", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("srceCopay", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("entityEffDate", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("channelCde", Arrays.asList(6, 1));
//                fieldWidthMultiMap.putAll("parentType", Arrays.asList(2, 1));
//                fieldWidthMultiMap.putAll("parentName", Arrays.asList(18, 1));
//                fieldWidthMultiMap.putAll("parentAgn", Arrays.asList(10, 1));
//                fieldWidthMultiMap.putAll("operation", Arrays.asList(1, 1));
//                fieldWidthMultiMap.putAll("clientExclInd", Arrays.asList(1, 1));
//                fieldWidthMultiMap.putAll("exclEntityTypCde", Arrays.asList(25, 0));
//                fieldWidthMultiMap.putAll("exclEntity", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("exclEntityAgn", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("exclEastGroup", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("networkType", Arrays.asList(3, 0));
//                fieldWidthMultiMap.putAll("networkId", Arrays.asList(15, 0));
//                fieldWidthMultiMap.putAll("chain", Arrays.asList(5, 0));
//                fieldWidthMultiMap.putAll("chainName", Arrays.asList(35, 0));
//                fieldWidthMultiMap.putAll("npiNumber", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("pharName", Arrays.asList(35, 0));
//                fieldWidthMultiMap.putAll("state", Arrays.asList(2, 0));
//                fieldWidthMultiMap.putAll("networkExcl", Arrays.asList(38));
//                fieldWidthMultiMap.putAll("networkExclInd", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("exclNetworkType", Arrays.asList(3, 0));
//                fieldWidthMultiMap.putAll("exclNetworkId", Arrays.asList(15, 0));
//                fieldWidthMultiMap.putAll("exclChain", Arrays.asList(5, 0));
//                fieldWidthMultiMap.putAll("exclNpiNumber", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("exclState", Arrays.asList(2, 0));
//                fieldWidthMultiMap.putAll("productType", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("productAttribute", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("productValue", Arrays.asList(20, 0));
//                fieldWidthMultiMap.putAll("hierarchyType", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("intentEndDate", Arrays.asList(10, 0));
//                fieldWidthMultiMap.putAll("validFlagRec", Arrays.asList(1, 0));
//                fieldWidthMultiMap.putAll("severity", Arrays.asList(18, 0));
//                fieldWidthMultiMap.putAll("category", Arrays.asList(14, 0));
//                fieldWidthMultiMap.putAll("message", Arrays.asList(500, 0));
//                fieldWidthMultiMap.putAll("userAction", Arrays.asList(256, 0));
//                fieldWidthMultiMap.putAll("procsStage", Arrays.asList(24, 0));
//                fieldWidthMultiMap.putAll("source", Arrays.asList(6, 0));
