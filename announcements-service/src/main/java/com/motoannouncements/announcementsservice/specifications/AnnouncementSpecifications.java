package com.motoannouncements.announcementsservice.specifications;

import com.motoannouncements.announcementsservice.dto.AnnouncementFilterRequest;
import com.motoannouncements.announcementsservice.entity.AnnouncementEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AnnouncementSpecifications {
    public static Specification<AnnouncementEntity> filterByCriteria(AnnouncementFilterRequest filterRequest) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            log.info("ROOT -> " + root.toString());
            if (filterRequest.getBrand() != null){
                predicates.add(builder.equal(root.get("brand"), filterRequest.getBrand()));
            }
            if (filterRequest.getModel() != null){
                predicates.add(builder.equal(root.get("model"), filterRequest.getModel()));
            }
            if (filterRequest.getCategory() != null){
                predicates.add(builder.equal(root.get("category"), filterRequest.getCategory()));
            }
            if (filterRequest.getCondition() != null){
                predicates.add(builder.equal(root.get("condition"), filterRequest.getCondition()));
            }
            if (filterRequest.getImported() != null){
                predicates.add(builder.equal(root.get("imported"), filterRequest.getImported()));
            }
            if (filterRequest.getAccidentFree() != null){
                predicates.add(builder.equal(root.get("accidentFree"), filterRequest.getAccidentFree()));
            }
            if (filterRequest.getColour() != null){
                predicates.add(builder.equal(root.get("color"), filterRequest.getColour()));
            }
            if (filterRequest.getDriveType() != null){
                predicates.add(builder.equal(root.get("driveType"), filterRequest.getDriveType()));
            }
            if (filterRequest.getGearboxType() != null){
                predicates.add(builder.equal(root.get("gearboxType"), filterRequest.getGearboxType()));
            }
            if (filterRequest.getMinYearOfProduction() != null || filterRequest.getMaxYearOfProduction() != null) {
                if (filterRequest.getMinYearOfProduction() != null && filterRequest.getMaxYearOfProduction() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("yearOfProduction"),
                            filterRequest.getMinYearOfProduction(), filterRequest.getMaxYearOfProduction()));
                } else if (filterRequest.getMinYearOfProduction() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("yearOfProduction"),
                            filterRequest.getMinYearOfProduction()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("yearOfProduction"),
                            filterRequest.getMaxYearOfProduction()));
                }
            }
            if (filterRequest.getMinPrice() != null || filterRequest.getMaxPrice() != null) {
                if (filterRequest.getMinPrice() != null && filterRequest.getMaxPrice() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("price"),
                            filterRequest.getMinPrice(), filterRequest.getMaxPrice()));
                } else if (filterRequest.getMinPrice() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("price"),
                            filterRequest.getMinPrice()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("price"),
                            filterRequest.getMaxPrice()));
                }
            }
            if (filterRequest.getCubicCapacityMin() != null || filterRequest.getCubicCapactiyMax() != null) {
                if (filterRequest.getCubicCapacityMin() != null && filterRequest.getCubicCapactiyMax() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("cubicCapacity"),
                            filterRequest.getCubicCapacityMin(), filterRequest.getCubicCapactiyMax()));
                } else if (filterRequest.getCubicCapacityMin() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("cubicCapacity"),
                            filterRequest.getCubicCapacityMin()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("cubicCapacity"),
                            filterRequest.getCubicCapactiyMax()));
                }
            }
            if (filterRequest.getNumberOfDoorsMin() != null || filterRequest.getNumberOfDoorsMax() != null) {
                if (filterRequest.getNumberOfDoorsMin() != null && filterRequest.getNumberOfDoorsMax() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("numberOfDoors"),
                            filterRequest.getNumberOfDoorsMin(), filterRequest.getNumberOfDoorsMax()));
                } else if (filterRequest.getNumberOfDoorsMin() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("numberOfDoors"),
                            filterRequest.getNumberOfDoorsMin()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("numberOfDoors"),
                            filterRequest.getNumberOfDoorsMax()));
                }
            }
            if (filterRequest.getNumberOfSeatsMin() != null || filterRequest.getNumberOfSeatsMax() != null) {
                if (filterRequest.getNumberOfSeatsMin() != null && filterRequest.getNumberOfSeatsMax() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("numberOfSeats"),
                            filterRequest.getNumberOfSeatsMin(), filterRequest.getNumberOfSeatsMax()));
                } else if (filterRequest.getNumberOfSeatsMin() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("numberOfSeats"),
                            filterRequest.getNumberOfSeatsMin()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("numberOfSeats"),
                            filterRequest.getNumberOfSeatsMax()));
                }
            }
            if (filterRequest.getPowerMin() != null || filterRequest.getPowerMax() != null) {
                if (filterRequest.getPowerMin() != null && filterRequest.getPowerMax() != null) {
                    // Both min and max values are provided, create a range predicate
                    predicates.add(builder.between(root.get("power"),
                            filterRequest.getPowerMin(), filterRequest.getPowerMax()));
                } else if (filterRequest.getPowerMin() != null) {
                    // Only min value is provided
                    predicates.add(builder.greaterThanOrEqualTo(root.get("power"),
                            filterRequest.getPowerMin()));
                } else {
                    // Only max value is provided
                    predicates.add(builder.lessThanOrEqualTo(root.get("power"),
                            filterRequest.getPowerMax()));
                }
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
