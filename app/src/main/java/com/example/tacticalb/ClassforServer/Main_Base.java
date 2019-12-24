package com.example.tacticalb.ClassforServer;

import java.util.List;

//Интерфейс для показа загрузки карт
public interface Main_Base {
    //Начало загрузки
    void showLonging();
    void hideLoading();
    //Обновление информации собственных карт
    void ResultHome(List<Scenario> scenario);
    //Обновление информации магазинных карт
    void ResultShop(List<Scenario> scenario);
//Штатное получение сценариев
    void onGetResult(List<Scenario> scenario);
    //Ошибки
    void onError(String localizedMessage);
}
