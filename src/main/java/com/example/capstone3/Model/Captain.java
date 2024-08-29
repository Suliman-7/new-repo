package com.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Captain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Captain name should not be empty!")
    @Size(min = 2, max = 25,message = "Captain name should be more than 2 and less than 25 chars!")
    @Pattern(regexp = "^[a-z A-Z]+$",message = "last name must contain only characters")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotNull(message = "Captain age should not be Empty!")
    @Positive(message = "Captain age should be positive number!")
    @Min(value = 25,message = "Captain age should be more than 25!")
    @Column(columnDefinition = "int not null")
    private int age;

    @NotEmpty(message = "vehicle should not be empty!")
    @Pattern(regexp = "^(sedan|bus|suv|minivan)$")
    @Column(columnDefinition = "varchar(7) not null")
    private String vehicle;

    @AssertTrue(message = "Licensed should be true!")
    @Column(columnDefinition = "boolean default true")
    private boolean licensed;

    @NotEmpty(message = "teacher phone number should not be empty!")
    @Size(min = 10,max = 10,message = "teacher phone number should be '10' digits")
    @Pattern(regexp = "^05\\d*$",message = "Phone number must start with '05' !")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;


    @Column(columnDefinition = "double not null ")
    private double rate = 0 ;

    @Column(columnDefinition = "int")
    private int complaints = 0 ;



    @Column(columnDefinition = "boolean default false")
    private boolean isTemporarySuspend=false;

    private LocalDate TemporarySuspendDate = null;

    private boolean activated = false ;


    @OneToMany(cascade = CascadeType.ALL , mappedBy = "captain")
    private Set<Student> students ;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "captain")
    private Set<Review> reviews ;




    @OneToMany(cascade = CascadeType.ALL , mappedBy = "captain")
    private List<DailyTrip> dailyTrips ;



    @OneToOne(cascade = CascadeType.ALL , mappedBy = "captain")
    private FacilityDeliveryGroup deliveryGroup;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "captain")
    private RequestRide requestRide;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "captain")
    private ParentRequestRide parentRequestRide;




}