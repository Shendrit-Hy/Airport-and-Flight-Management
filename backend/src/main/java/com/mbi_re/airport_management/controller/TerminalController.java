package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.TerminalDTO;
import com.mbi_re.airport_management.service.TerminalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminals")
public class TerminalController {

    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping
    public List<TerminalDTO> getAllTerminals() {
        return terminalService.getAllTerminals();
    }

    @PostMapping
    public TerminalDTO createTerminal(@RequestBody TerminalDTO dto) {
        return terminalService.createTerminal(dto);
    }
}
