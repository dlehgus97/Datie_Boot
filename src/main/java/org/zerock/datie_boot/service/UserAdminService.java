package org.zerock.datie_boot.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.UserAdminDTO;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardAdminRepository;
import org.zerock.datie_boot.repository.UserAdminRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAdminService {

    @Autowired
    UserAdminRepository userAdminRepository;

    @Autowired
    CardAdminRepository cardAdminRepository;

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
}
