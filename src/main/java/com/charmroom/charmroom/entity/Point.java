package com.charmroom.charmroom.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.charmroom.charmroom.entity.enums.PointType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Point {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private User user;
	
	@CreationTimestamp
	private LocalDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
	private PointType type;
	
	@Column(nullable = false)
	@Builder.Default
	private Integer diff = 0;
}
