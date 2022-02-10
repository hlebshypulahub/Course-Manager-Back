package com.hs.coursemanagerback.controller.principal;

import com.hs.coursemanagerback.service.user.PrincipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/principal")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class PrincipalController {

    private final PrincipleService principleService;

    @Autowired
    public PrincipalController(PrincipleService principleService) {
        this.principleService = principleService;
    }

    @GetMapping("")
    public String getPrincipalCompany() {
        return principleService.getPrincipalCompany();
    }

}
