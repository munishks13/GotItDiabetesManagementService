package com.capstone.coursera.gidma.service.utils.json;

import static com.capstone.coursera.gidma.service.utils.Utils.SDF_1;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    Logger LOG = LoggerFactory.getLogger(JsonDateSerializer.class);
    

    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        //LOG.info("\n =====================>>>>>> JsonDateSerializer : date : " + date + "\n");

        if (date != null) {
            String formattedDate = SDF_1.format(date);
            //LOG.info("\n\n =====================>>>>>> JsonDateSerializer : formattedDate : " + formattedDate + "\n\n");
            jgen.writeString(formattedDate);
        }

    }

}
