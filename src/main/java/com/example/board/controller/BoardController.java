package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String save(){
        return "boardPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "redirect:/board";
    }

    @GetMapping
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/boardList";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        boardService.increaseHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "/boardPages/boardDetail";
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boardService.deleteById(id);
        return "redirect:/board";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByAxios(@PathVariable("id") Long id){
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        BoardDTO byId = boardService.findById(id);
        model.addAttribute("board", byId);
        return "boardPages/boardUpdate";
    }

}
