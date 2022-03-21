package com.fordevs.jsontotextformat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class Controller {

//    @PostMapping(path= "/cit-job", consumes = "application/json")
//    public ResponseEntity convertJsonToFlatFile(@RequestBody HashMap<String, Object>[] body ) e{
//        return ResponseEntity.ok(Service.convertJsonToFlatFile(body));
//    }

    @PostMapping(value="/cit-job")
    public void jsonToFileFormat(@RequestBody JsonNode jsonCitRules) throws JsonProcessingException {

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


        jsonCitRules.forEach( jsonCitRule -> {

            log.info("jsonCitRule : {} ",  jsonCitRule );

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(String.valueOf(jsonCitRule));
                String nationId = jsonNode.get("ID Nation").asText();
                String nation = jsonNode.get("ID Nation").asText();


                String citRule = String.format("%10s %20s", nationId, nation);

                log.info(citRule);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
