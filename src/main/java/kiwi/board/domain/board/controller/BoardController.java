package kiwi.board.domain.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kiwi.board.annotation.LoginUser;
import kiwi.board.domain.board.model.request.BoardsRequest;
import kiwi.board.domain.board.model.request.SaveBoardRequest;
import kiwi.board.domain.board.model.request.UpdateBoardRequest;
import kiwi.board.domain.board.model.response.BoardResponse;
import kiwi.board.domain.board.service.BoardService;
import kiwi.board.common.model.request.JwtUserRequest;
import kiwi.board.domain.board.service.query.BoardQueryService;
import kiwi.board.util.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Board", description = "게시판 본문")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/boards")
public class BoardController {

    private final BoardQueryService boardQueryService;
    private final BoardService boardService;
    private final Validator validator;

    @GetMapping
    @ApiOperation(value = "게시판 LIST 출력", notes = "게시판 LIST 를 출력합니다. ")
    public ResponseEntity<?> getBoards(@ModelAttribute BoardsRequest boardsRequest) {

        return ResponseEntity.ok().body(boardQueryService.getBoards(boardsRequest));
    }

    @GetMapping("{boardNo}")
    @ApiOperation(value = "게시판 상세 조회", notes = "게시판을 조회 합니다.")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable long boardNo) {

        BoardResponse boardResponse = boardQueryService.getBoard(boardNo);

        return (boardResponse == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(boardResponse);
    }

    @PostMapping
    @ApiOperation(value = "게시판 저장", notes = "게시판을 저장 합니다")
    public ResponseEntity<?> saveBoard(@LoginUser JwtUserRequest jwtUserRequest, @RequestBody SaveBoardRequest saveBoardRequest) {

        validator.saveBoard(saveBoardRequest);

        boardService.saveBoard(jwtUserRequest.getMember_seq(), saveBoardRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{boardNo}")
    @ApiOperation(value = "게시판 수정", notes = "게시판을 수정 합니다")
    public ResponseEntity<?> updateBoard(@LoginUser JwtUserRequest jwtUserRequest, @PathVariable long boardNo, @RequestBody UpdateBoardRequest updateBoardRequest) {

        validator.updateBoard(updateBoardRequest);

        boardService.updateBoard(jwtUserRequest.getMember_seq(), boardNo, updateBoardRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{boardNo}")
    @ApiOperation(value = "게시판 삭제", notes = "게시판을 식제 합니다")
    public ResponseEntity<?> deleteBoard(@LoginUser JwtUserRequest jwtUserRequest, @PathVariable long boardNo) {

        boardService.deleteBoard(jwtUserRequest.getMember_seq(), boardNo);
        return ResponseEntity.noContent().build();
    }
}
