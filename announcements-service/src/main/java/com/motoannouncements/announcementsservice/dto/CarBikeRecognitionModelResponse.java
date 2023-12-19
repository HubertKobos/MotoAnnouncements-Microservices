package com.motoannouncements.announcementsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarBikeRecognitionModelResponse {
    private String recognition_class;
    private String probability;
}
