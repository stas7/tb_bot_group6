package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum

@Configuration
class StateBeanConfig {
    @Autowired
    lateinit var initState: InitState
    @Autowired
    lateinit var failureState: FailureState
    @Autowired
    lateinit var decidingFromInitState: DecidingFromInitState
    @Autowired
    lateinit var myMeetingsState: MyMeetingsState
    @Autowired
    lateinit var meetingDetailsState: MeetingDetailsState
    @Autowired
    lateinit var listOfCitiesState: ListOfCitiesState
    @Autowired
    lateinit var choosingCityState: ChoosingCityState
    @Autowired
    lateinit var createOrSearchMeetingsState: CreateOrSearchMeetingState
    @Autowired
    lateinit var meetingCreationNameState: MeetingCreationNameState
    @Autowired
    lateinit var meetingCreationAddressState: MeetingCreationAddressState
    @Autowired
    lateinit var meetingCreationDateTimeState: MeetingCreationDateTimeState
    @Autowired
    lateinit var listMeetingsInCityState: ListMeetingsInCityState
    @Autowired
    lateinit var decidingFromMeetingDetailsState: DecidingFromMeetingDetailsState


    @Bean
    fun stateComponents(): Map<MachinesStateEnum, StateInterface> {
        return mapOf(MachinesStateEnum.INIT to initState,
            MachinesStateEnum.FAILED to failureState,
            MachinesStateEnum.DECIDING_FROM_INIT to decidingFromInitState,
            MachinesStateEnum.MY_MEETINGS to myMeetingsState,
            MachinesStateEnum.MEETING_DETAILS to meetingDetailsState,
            MachinesStateEnum.LIST_OF_CITIES to listOfCitiesState,
            MachinesStateEnum.CHOOSING_CITY to choosingCityState,
            MachinesStateEnum.CREATE_OR_SEARCH_MEETING to createOrSearchMeetingsState,
            MachinesStateEnum.MEETING_CREATION_NAME to meetingCreationNameState,
            MachinesStateEnum.MEETING_CREATION_ADDRESS to meetingCreationAddressState,
            MachinesStateEnum.MEETING_CREATION_DATETIME to meetingCreationDateTimeState,
            MachinesStateEnum.LIST_MEETINGS_IN_CITY to listMeetingsInCityState,
            MachinesStateEnum.DECIDING_FROM_MEETING_DETAILS to decidingFromMeetingDetailsState
        )



    }
}