package com.fordevs.jsontotextformat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class Controller {

//    @PostMapping(path= "/cit-job", consumes = "application/json")
//    public ResponseEntity convertJsonToFlatFile(@RequestBody HashMap<String, Object>[] body ) e{
//        return ResponseEntity.ok(Service.convertJsonToFlatFile(body));
//    }

    @PostMapping(value = "/cit-job")
    public List<String> jsonToFileFormat(@RequestBody JsonNode jsonCitRules) throws IOException {


        List<String> citRulesList = new ArrayList<>();
        String nationId = null;
        String nation = null;
        String male = null;

        for (JsonNode jsonCitRule : jsonCitRules) {
            log.info("jsonCitRule : {} ", jsonCitRule);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootJsonNode;
            try {
                rootJsonNode = objectMapper.readTree(String.valueOf(jsonCitRule));
                //parse data to variables
                nationId = rootJsonNode.at("/ID Nation").asText();
                nation = rootJsonNode.at("/Nation").asText();
                ArrayNode genderPopulation = (ArrayNode) objectMapper.readTree(String.valueOf(jsonCitRule)).at("/Gender Population");
                if (genderPopulation.isArray()) {
                    for (JsonNode genderPopulationElement : genderPopulation) {
                        System.out.println(genderPopulationElement);
                        male = genderPopulationElement.at("/Male").asText();
                    }
                }

                String citRule = String.format("%10s %30s %15s\n", nationId, nation, male);
                log.info(citRule);
                citRulesList.add(citRule);


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        log.info(String.valueOf(citRulesList));

        FileWriter fileWriter = new FileWriter("src/main/resources/reports/report_" + nationId + ".txt");
        for (String citRule : citRulesList) {
//
            fileWriter.write(citRule );
        }
        fileWriter.close();

        //TODO:SFTP to MainFrame
        //TODO: After SFTP to Mainframe delete text report file

        return citRulesList;
    }
}


/*
 * This method parse JSON String by using Jackson Streaming API example.
 */
//        String nation;
//            try {
//                JsonFactory factory  = new JsonFactory();
//                JsonParser parser = factory.createParser(jsonCitRules);
//
//                while (!parser.isClosed()) {
//                    //if the nextToken is a field name
//                    if (parser.nextToken() == JsonToken.FIELD_NAME) {
//                        log.info(parser.getCurrentName());
//                    }
//                }
//
//                // starting parsing of JSON String
//                while (parser.nextToken() != JsonToken.END_OBJECT) {
//                    String token = parser.getCurrentName();
//
//                    if ("Nation".equals(token)) {
//                        parser.nextToken();  //next token contains value
//                        nation = parser.getText();  //getting text field
//                        log.info("Nation : " + nation);
//                    }
////                    if ("lastname".equals(token)) {
////                        parser.nextToken();
////                        String lname = parser.getText();
////                        System.out.println("lastname : " + lname);
////
////                    }
////
////                    if ("phone".equals(token)) {
////                        parser.nextToken();
////                        int phone = parser.getIntValue();
////                        // getting numeric field
////                        System.out.println("phone : " + phone);
////
////                    }
////
////                    if ("address".equals(token)) {
////                        System.out.println("address :");
////                        parser.nextToken();
////                        // next token will be '[' which means JSON array
////
////                        // parse tokens until you find ']'
////                        while (parser.nextToken() != JsonToken.END_ARRAY) {
////                            System.out.println(parser.getText());
////                        }
////                    }
//                }
//                parser.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        int rowNumber = 1;
//        List<String> citRuleList;
//        JsonNode jsonNode = null;
//
//        jsonCitRuleList.forEach( row -> {
//            log.info("row : {} ",  row );
//
//            String idNation = jsonNode.get("ID Nation").asText();
//            log.info(idNation);
//        });

//        System.out.println();
//        String jsonHiWorld = "{\"message\":\"Hi\",\"place\":{\"name\":\"World!\"}}\"";
//        String message = mapper.readTree(jsonHiWorld).at("/message").asText();
//        String place = mapper.readTree(jsonHiWorld).at("/place/name").asText();
//        System.out.println(message + " " + place); // print "Hi World!"

//
//            log.info("jsonCitRule : {} ",  jsonCitRules );
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootJsonNode = objectMapper.readTree(String.valueOf(jsonCitRules));
//
//                String nationId = rootJsonNode.at("/ID Nation").asText();
//                String nation = rootJsonNode.at("/Nation").asText();
//                ArrayNode genderPopulation= (ArrayNode) objectMapper.readTree(String.valueOf(jsonCitRules)).at("Gender Population");
//                if(genderPopulation.isArray()) {
//                    for(JsonNode genderPopulationElement : genderPopulation) {
//                        System.out.println(genderPopulationElement);
//                    }
//                }
//
//                String citRule = String.format("%10s %30s %15s", nationId, nation);
//                log.info(citRule);
//
//
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }