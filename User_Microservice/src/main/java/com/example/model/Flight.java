package com.example.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="flight")
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="flight_id")
	@ApiModelProperty(notes="Unique id for the flight")
	private int flightId;

	//@NotEmpty
	@Column(name="arrival_location")
	@ApiModelProperty(notes="Destination of the Flight")
	private String arrivalLoc;

	//@NotEmpty
	@Column(name="departure_location")
	@ApiModelProperty(notes="Origin of the flight")
	private String departureLoc;

	//@NotEmpty
	@Column(name="fleet_name")
	@ApiModelProperty(notes="Name of the flight")
	private String fleetName;

	//@NotEmpty
	@Column(name="model")
	@ApiModelProperty(notes="Model of the flight")
	private String model;

	//@NotNull
	@Column(name="remaining_seats")
	@ApiModelProperty(notes="Remaining seats available in the flight")
	private Integer remainingSeats;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name="flightdate")
	@ApiModelProperty(notes="Departure date of the flight")
	LocalDate date;

	public Flight() {
		super();
	}


	
	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", arrivalLoc=" + arrivalLoc + ", departureLoc=" + departureLoc
				+ ", fleetName=" + fleetName + ", model=" + model + ", remainingSeats=" + remainingSeats + ", date="
				+ date + "]";
	}

	public Flight(int flightId, String arrivalLoc, String departureLoc, String fleetName, String model,
			Integer remainingSeats,LocalDate date) {
		super();
		this.flightId = flightId;
		this.arrivalLoc = arrivalLoc;
		this.departureLoc = departureLoc;
		this.fleetName = fleetName;
		this.model = model;
		this.remainingSeats = remainingSeats;
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getArrivalLoc() {
		return arrivalLoc;
	}

	public void setArrivalLoc(String arrivalLoc) {
		this.arrivalLoc = arrivalLoc;
	}

	public String getDepartureLoc() {
		return departureLoc;
	}

	public void setDepartureLoc(String departureLoc) {
		this.departureLoc = departureLoc;
	}

	public String getFleetName() {
		return fleetName;
	}

	public void setFleetName(String fleetName) {
		this.fleetName = fleetName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(Integer remainingSeats) {
		this.remainingSeats = remainingSeats;
	}
	
}
