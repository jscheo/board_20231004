package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }
    /*
        rest api
        /board/10 => 10번글
        /board/20 => 20번글
         늘 바뀔 수 있다.
        3페이지에 있는 15번글
        /board/3/15
        /board/15?page=3
     */
    @GetMapping
    public String findAll(Model model,
                        @RequestParam(value="page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q){
        Page<BoardDTO> boardDTOList = boardService.findAll(page, type, q);
        // 목록 하단에 보여줄 페이지 번호
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();
//        if ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) {
//            endPage = startPage + blockLimit - 1;
//        } else {
//            endPage = boardDTOS.getTotalPages();
//        }
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "boardPages/boardList";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        boardService.increaseHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "boardPages/boardDetail";
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
        return "/boardPages/boardUpdate";
    }

//    @PostMapping("/board/update")
//    public String update(@ModelAttribute BoardDTO boardDTO){
//        boardService.update(boardDTO);
//        return "/boardPages/boardDetail";
//    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO){
        boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
