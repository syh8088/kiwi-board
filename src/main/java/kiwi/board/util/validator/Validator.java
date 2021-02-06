package kiwi.board.util.validator;

import kiwi.board.board.model.request.SaveBoardRequest;
import kiwi.board.board.model.request.UpdateBoardRequest;
import kiwi.board.board.service.BoardService;
import kiwi.board.error.errorCode.BoardErrorCode;
import kiwi.board.error.errorCode.MemberErrorCode;
import kiwi.board.error.exception.BusinessException;
import kiwi.board.member.model.request.SaveMemberRequest;
import kiwi.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;


@Component
@RequiredArgsConstructor
public class Validator {

    private final MemberService memberService;
    private final BoardService boardService;

    public void saveMember(SaveMemberRequest saveMemberRequest) {

        if (StringUtils.isEmpty(saveMemberRequest.getId()) ||  StringUtils.isEmpty(saveMemberRequest.getName()) || StringUtils.isEmpty(saveMemberRequest.getPassword()) || StringUtils.isEmpty(saveMemberRequest.getEmail())) {
            throw new BusinessException(MemberErrorCode.NO_REQUIRED_INFORMATION);
        }

        if (saveMemberRequest.getPassword().length() < 10) {
            throw new BusinessException(MemberErrorCode.NOT_VALID_PASSWORD_LENGTH);
        }

        if (memberService.isAlreadyRegisteredId(saveMemberRequest.getId())) {
            throw new BusinessException(MemberErrorCode.ALREADY_JOIN_ID);
        }

        if (memberService.isAlreadyRegisteredEmail(saveMemberRequest.getEmail())) {
            throw new BusinessException(MemberErrorCode.ALREADY_JOIN_EMAIL);
        }
    }

    public void saveBoard(SaveBoardRequest saveBoardRequest) {

        if (StringUtils.isEmpty(saveBoardRequest.getTitle()) || StringUtils.isEmpty(saveBoardRequest.getContent())) {
            throw new BusinessException(BoardErrorCode.NOT_EXIST_TITLE_AND_CONTENT);
        }
    }

    public void updateBoard(UpdateBoardRequest updateBoardRequest) {

        if (StringUtils.isEmpty(updateBoardRequest.getTitle()) || StringUtils.isEmpty(updateBoardRequest.getContent())) {
            throw new BusinessException(BoardErrorCode.NOT_EXIST_TITLE_AND_CONTENT);
        }
    }

    private void isBoardTitleAndContent(String title, String content) {
        if (title.isEmpty() || content.isEmpty()) {
            throw new BusinessException(BoardErrorCode.NOT_EXIST_TITLE_AND_CONTENT);
        }
    }
}
