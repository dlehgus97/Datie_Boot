package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.UserAdminDTO;
import org.zerock.datie_boot.service.UserAdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
public class UserAdminController {

    @Autowired
    private UserAdminService userAdminService;


    @GetMapping("/admin/time")
    public List<String> adminGetTime(UserAdminDTO param) {
        return userAdminService.adminGetTime();
    }


    @GetMapping("/admin/age")
    public List<Map<String,String>> adminGetAge(UserAdminDTO param) {
        return userAdminService.adminGetAge();
    }



    @GetMapping("/admin/category")
    public List<Map<String,String>> adminGetCategory(UserAdminDTO param) {
        return userAdminService.adminGetCategory();
    }






    @GetMapping("/admin/today")
    public String adminGetToday(UserAdminDTO param) {
        return userAdminService.adminGetToday();
    }





    @GetMapping("/admin/list")
    public List<UserAdminDTO> adminGetList(UserAdminDTO param) {
        return userAdminService.adminGetList(param);
    }

    @PostMapping("/admin/list")
    public ResponseEntity<String> bulkActivate(@RequestBody ActivateRequest activateRequest) {
        System.out.println(activateRequest.getFlag());
        System.out.println("선택된 행 ID: " + activateRequest.getSelectedRows());

        // 예시: 선택된 ID에 대한 처리 로직
        for (String id : activateRequest.getSelectedRows()) {
            if(activateRequest.getFlag().equals("deactive")){
                userAdminService.deactivateByUserId(id);
            }else if(activateRequest.getFlag().equals("hold")){
                userAdminService.holdByUserId(id);
            }else {

            }

        }

        return ResponseEntity.ok("일괄 처리가 완료되었습니다.");
    }

}

// 요청 데이터 클래스를 정의합니다.
class ActivateRequest {
    private List<String> selectedRows;
    private String flag;

    // Getters 및 Setters
    public List<String> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<String> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}