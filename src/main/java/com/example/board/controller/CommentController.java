package com.example.board.controller;

import com.example.board.dto.CommentDTO;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save/{id}")
    public ResponseEntity save (@RequestBody CommentDTO commentDTO,
                                @PathVariable("id") Long id){
        CommentDTO save = commentService.save(commentDTO, id);
        List<CommentDTO> commentDTOList = commentService.findAll();
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
}
