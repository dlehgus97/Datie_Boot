package org.zerock.datie_boot.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.UserAdminDTO;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardAdminRepository;
import org.zerock.datie_boot.repository.PaymentRecordRepository;
import org.zerock.datie_boot.repository.UserAdminRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserAdminService {

    @Autowired
    UserAdminRepository userAdminRepository;

    @Autowired
    CardAdminRepository cardAdminRepository;

    @Autowired
    PaymentRecordRepository paymentRecordRepository;

    public List<UserAdminDTO> adminGetList(UserAdminDTO param) {
        List<Object[]> list;
        if(param.getSearchType() != null && !"".equals(param.getSearchType()) && param.getSearchWord() != null && !"".equals(param.getSearchWord()) ){
            if(param.getSearchType().equals("all")){
                list = userAdminRepository.adminGetList();
            }else if (param.getSearchType().equals("num")){
                list = userAdminRepository.adminGetListByNum(param.getSearchWord());
            }else if (param.getSearchType().equals("id")){
                list = userAdminRepository.adminGetListById(param.getSearchWord());
            }else if (param.getSearchType().equals("name")){
                list = userAdminRepository.adminGetListByName(param.getSearchWord());
            }else if (param.getSearchType().equals("ms")){
                list = userAdminRepository.adminGetListByMs(param.getSearchWord());
            }else if (param.getSearchType().equals("cs")){
                list = userAdminRepository.adminGetListByCs(param.getSearchWord());
            }else {
                list = userAdminRepository.adminGetList();
            }
        }else {
            list = userAdminRepository.adminGetList();
        }











        ArrayList<UserAdminDTO> result = new ArrayList<UserAdminDTO>();
        list.forEach(row ->{
            User user = (User)row[0];
            Card card = (Card)row[1];
            UserAdminDTO userAdminDTO = new UserAdminDTO();
            userAdminDTO.setId(user.getUserno());
            userAdminDTO.setUserId(user.getId());
            userAdminDTO.setName(user.getName());
            userAdminDTO.setStatus(user.getStatus());
            userAdminDTO.setModdate(user.getModdate());

            if(card != null) {
                userAdminDTO.setCStatus(card.getCStatus());
            }
            result.add(userAdminDTO);

        });
        return result;
    }


    @Transactional
    public void deactivateByUserId(String id) {
        String maxDate = cardAdminRepository.getMaxDate(id);
        Timestamp timestamp = Timestamp.valueOf(maxDate);
        cardAdminRepository.deactivateByUserId(id , timestamp);
        cardAdminRepository.zeroCardno(id);
    }

    @Transactional
    public void holdByUserId(String id) {
        String maxDate = cardAdminRepository.getMaxDate(id);
        Timestamp timestamp = Timestamp.valueOf(maxDate);
        cardAdminRepository.holdByUserId(id , timestamp);
    }

    public String adminGetToday() {
        LocalDate today = LocalDate.now();
        Date date = java.sql.Date.valueOf(today);
        return paymentRecordRepository.adminGetToday(date);
    }

    public List<Map<String, String>> adminGetCategory() {
        List<Object[]> results = paymentRecordRepository.adminGetCategory();
        return IntStream.range(0, results.size())
                .mapToObj(i -> {
                    Object[] result = results.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id" , i+"" );
                    map.put("label", (String) result[0]); // 카테고리 이름
                    map.put("value", String.valueOf(result[1])); // 결제 내역 수
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> adminGetAge() {
        List<Object[]> results = paymentRecordRepository.adminGetAge();
        List<Map<String, String>> ageDataList = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, String> ageData = new HashMap<>();
            ageData.put("ageGroup", (String) result[0]); // ageGroup
            ageData.put("외식", String.valueOf(result[1])); // 외식
            ageData.put("미용패션", String.valueOf(result[2])); // 미용/패션
            ageData.put("문화여가", String.valueOf(result[3])); // 문화/여가
            ageData.put("식료품", String.valueOf(result[4])); // 식료품
            ageData.put("기타", String.valueOf(result[5])); // 기타

            ageDataList.add(ageData);
        }

        return ageDataList;
    }

    public List<String> adminGetTime() {
        List<Object[]> results = paymentRecordRepository.adminGetTime();
        List<String> timeDataList = new ArrayList<>();
        if(results.size() == 1){
            Object[] objects = results.get(0);
            timeDataList.add(String.valueOf(objects[0]));
            timeDataList.add(String.valueOf(objects[1]));
            timeDataList.add(String.valueOf(objects[2]));
            timeDataList.add(String.valueOf(objects[3]));
            timeDataList.add(String.valueOf(objects[4]));
            timeDataList.add(String.valueOf(objects[5]));
            timeDataList.add(String.valueOf(objects[6]));
            timeDataList.add(String.valueOf(objects[7]));
            timeDataList.add(String.valueOf(objects[8]));
            timeDataList.add(String.valueOf(objects[9]));
            timeDataList.add(String.valueOf(objects[10]));
            timeDataList.add(String.valueOf(objects[11]));
            timeDataList.add(String.valueOf(objects[12]));
            timeDataList.add(String.valueOf(objects[13]));
            timeDataList.add(String.valueOf(objects[14]));
            timeDataList.add(String.valueOf(objects[15]));
            timeDataList.add(String.valueOf(objects[16]));
            timeDataList.add(String.valueOf(objects[17]));
            timeDataList.add(String.valueOf(objects[18]));
            timeDataList.add(String.valueOf(objects[19]));
            timeDataList.add(String.valueOf(objects[20]));
            timeDataList.add(String.valueOf(objects[21]));
            timeDataList.add(String.valueOf(objects[22]));
            timeDataList.add(String.valueOf(objects[23]));

            return timeDataList;
        } else {
            return null;
        }

    }
}
