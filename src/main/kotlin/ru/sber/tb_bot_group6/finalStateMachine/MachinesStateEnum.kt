package ru.sber.tb_bot_group6.finalStateMachine
// state in the final state machine
enum class MachinesStateEnum (val command: String, val desc: String) {
    START("start", "start bot"), //начало работы бота
    CHOOSE_CITY_BUTTON("city_button", "choose city"), //customer записывается город в котором он будет искать/организовывать встречи
    SEARCH_MEETING_BUTTON("search_meeting_button", "search for meeting"), //тут customer'у будет присваиваться роль USER?
    ORGANISE_MEETING_BUTTON("organise_meeting", "organise new meeting"), //тут customer'у будет присваиваться роль CREATOR?
    ADD_NAME_OF_MEETING("name_of_meeting", "add name of meeting"), //CREATOR создает встречу и пишет ее название
    ADD_DESCRIPTION_OF_MEETING("description_of_meeting", "add description when organise meeting"), //CREATOR добавляет описание ко встрече
    ADD_POINT_OF_MEETING("point_of_meeting" , "add meeting location"), //CREATOR добавляет адрес встречи
    ACCEPT_MEETING_BUTTON("accept_meeting", "user join meeting"), //customer записывается на встречу
}
