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
@RequestMapping("/pharmacylevelbenefit/v1")
@Slf4j
public class Controller {

//    @PostMapping(path= "/cit-job", consumes = "application/json")
//    public ResponseEntity convertJsonToFlatFile(@RequestBody HashMap<String, Object>[] body ) {
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

        jsonCitRules.forEach( jsonCitRule -> {
            log.info("jsonCitRule : {} ",  jsonCitRule );

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(String.valueOf(jsonCitRule));
                System.out.println(jsonNode.get("Nation").asText());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
