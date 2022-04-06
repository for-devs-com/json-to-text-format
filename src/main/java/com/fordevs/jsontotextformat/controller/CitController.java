package com.fordevs.jsontotextformat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@RestController
@RequestMapping("/pharmacylevelbenefit/v1/partialintent")
@Slf4j
public class CitController {

    @PostMapping(value = "/cit-mainframe-connector")
    public void citMainframeConnector(@RequestBody JsonNode jsonNode) {
        /*
        * */
        try {
            StringBuilder citRules = new StringBuilder();
            for (JsonNode jsonNodeCitRule : jsonNode) {
                citRules.append(traverse(jsonNodeCitRule));
            }
            System.out.println(citRules);
            bufferedWriter(citRules);

            //TODO:SFTP to MainFrame
            //TODO: After SFTP to Mainframe delete text report file
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    //get all field values per each cit rule
    public StringBuilder traverse(JsonNode jsonNode) throws IOException {

        StringBuilder citRule = new StringBuilder();

        //get all the fields in this JsonNode
        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValueNode = jsonNode.get(fieldName);


                if (fieldValueNode.isArray()) {
                    for (JsonNode valueNode : fieldValueNode) {
                        Iterator<String> arrayFieldNames = valueNode.fieldNames();
                        while (arrayFieldNames.hasNext()) {
                            fieldName = arrayFieldNames.next();
                            JsonNode arrayFieldValueNode = valueNode.get(fieldName);
                            String fieldValue = arrayFieldValueNode.asText();
                            log.info("fieldName  : {}", fieldName);
                            log.info("fieldValue = {}", fieldValue);
                            citRule.append(formatFieldValue(fieldName, fieldValue));
                        }
                    }
                } else {
                    String fieldValue = fieldValueNode.asText();
                    log.info("fieldName  : {}", fieldName);
                    log.info("fieldValue = {}", fieldValue);
                    citRule.append(formatFieldValue(fieldName, fieldValue));
                }
            }
            citRule.append("\n");
        }
        return citRule;
    }

    public StringBuilder formatFieldValue(String fieldName, String fieldValue) {
        //List<String> columnWidths = List.of("36", "10", "8", "6", "1", "18", "14", "500", "256", "24","6", "2");
        //modify the string format for each field
        HashMap<String, String> fieldWidthMap = new HashMap<>();

        //TODO:Make this configurable https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
        //TODO: Use MultiMapValue https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html
        fieldWidthMap.put("flowId", "36");
        fieldWidthMap.put("ptmRequestId", "10");
        fieldWidthMap.put("operId", "8");
        fieldWidthMap.put("recordCount", "6");
        fieldWidthMap.put("validFlagFile", "1");
        fieldWidthMap.put("severityF", "18");
        fieldWidthMap.put("categoryF", "14");
        fieldWidthMap.put("messageF", "500");
        fieldWidthMap.put("userActionF", "256");
        fieldWidthMap.put("procsStageF", "24");
        fieldWidthMap.put("sourceF", "6");
        fieldWidthMap.put("setUpChangeNumber", "2");
        fieldWidthMap.put("rowNumber", "10");
        fieldWidthMap.put("entityType", "25");
        fieldWidthMap.put("entityOpId", "18");
        fieldWidthMap.put("entityAgn", "10");
        fieldWidthMap.put("eastGrpOpId", "18");
        fieldWidthMap.put("srceEntity", "18");
        fieldWidthMap.put("srceEntityGr", "18");
        fieldWidthMap.put("srceCopay", "18");
        fieldWidthMap.put("entityEffDate", "10");
        fieldWidthMap.put("channelCde", "6");
        fieldWidthMap.put("parentType", "2");
        fieldWidthMap.put("parentName", "18");
        fieldWidthMap.put("parentAgn", "10");
        fieldWidthMap.put("operation", "1");
        fieldWidthMap.put("clientExclInd", "1");
        fieldWidthMap.put("exclEntityTypCde", "2");
        fieldWidthMap.put("exclEntity", "18");
        fieldWidthMap.put("exclEntityAgn", "10");
        fieldWidthMap.put("exclEastGroup", "18");
        fieldWidthMap.put("networkType", "3");
        fieldWidthMap.put("networkId", "15");
        fieldWidthMap.put("chain", "5");
        fieldWidthMap.put("chainName", "35");
        fieldWidthMap.put("npiNumber", "10");
        fieldWidthMap.put("pharName", "35");
        fieldWidthMap.put("state", "2");
        fieldWidthMap.put("networkExclInd", "1");
        fieldWidthMap.put("exclNetworkType", "3");
        fieldWidthMap.put("exclNetworkId", "15");
        fieldWidthMap.put("exclChain", "5");
        fieldWidthMap.put("exclNpiNumber", "10");
        fieldWidthMap.put("exclState", "2");
        fieldWidthMap.put("productType", "20");
        fieldWidthMap.put("productAttribute", "20");
        fieldWidthMap.put("productValue", "20");
        fieldWidthMap.put("hierarchyType", "1");
        fieldWidthMap.put("intentEndDate", "10");
        fieldWidthMap.put("validFlagRec", "1");
        fieldWidthMap.put("severity", "18");
        fieldWidthMap.put("category", "14");
        fieldWidthMap.put("message", "500");
        fieldWidthMap.put("userAction", "256");
        fieldWidthMap.put("procsStage", "24");
        fieldWidthMap.put("source", "6");

        StringBuilder formatFieldValue = new StringBuilder();
        if (fieldWidthMap.containsKey(fieldName)) {
            String fieldWidth = fieldWidthMap.get(fieldName);
            String fillerWidth;
            String filler = "";
            if (fieldName.equals("flowId") ||
                    fieldName.equals("ptmRequestId") ||
                    fieldName.equals("operId") ||
                    fieldName.equals("recordCount") ||
                    fieldName.equals("validFlagFile") ||
                    fieldName.equals("severityF") ||
                    fieldName.equals("categoryF") ||
                    fieldName.equals("messageF") ||
                    fieldName.equals("userActionF") ||
                    fieldName.equals("procsStageF") ||
                    fieldName.equals("clientExclInd")) {
                fillerWidth = "1";
                formatFieldValue.append(String.format("%" + fieldWidth + "s %" + fillerWidth + "s", fieldValue, filler));
            } else if (fieldName.equals("sourceF")) {
                fillerWidth = "1252";
                formatFieldValue.append(String.format("%" + fieldWidth + "s %" + fillerWidth + "s", fieldValue, filler));
            } else {
                formatFieldValue.append(String.format("%" + fieldWidth + "s", fieldValue));
            }
        }
        return formatFieldValue;
    }


}
