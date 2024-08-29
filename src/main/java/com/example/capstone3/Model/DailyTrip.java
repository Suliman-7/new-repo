package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class DailyTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "double not null")
    private int period;

    private double price = 0 ;

    @NotEmpty(message = "start point should be not null")
    @Column(columnDefinition = "varchar(20) not null")
    private String startPoint;

    @NotEmpty(message = "destination should be not null")
    @Column(columnDefinition = "varchar(20) not null")
    private String destination;

    @NotNull(message = "leave Hour should not be empty!")
    @JsonFormat(pattern = "hh:mm")
    @Column(columnDefinition = "time not null")
    private Time leaveHour;

    @ManyToOne
    @JsonIgnore
    private Captain captain;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "dailyTrip")
    private Set<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dailyTrip")
    private Set<SuccessorStudent> successors;


}
