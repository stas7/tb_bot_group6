package ru.sber.tb_bot_group6.persistence.entity

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "customer_meetings")

final class MeetingsEntity (
        @Id
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", referencedColumnName = "id")
        var customer: CustomersEntity? = null,

        @Id
        @Column(name = "meeting_id")
        var meetingId: Long,

        var authority: Int
)