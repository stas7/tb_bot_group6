package ru.sber.tb_bot_group6.persistence.entity

import org.hibernate.annotations.NaturalId
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "cities")
data class CityEntity(
    @Id
    val id: Long,

    @Column(name = "city_name")
    @NaturalId
    val name: String
)
