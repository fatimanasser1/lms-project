package com.example.lmsn.borrow;

import com.example.lmsn.borrow.dto.BorrowResponse;
import com.example.lmsn.borrow.dto.CreateBorrowRequest;
import com.example.lmsn.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class MemberBorrowController {

    private final BorrowService borrowService;

    public MemberBorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public BorrowResponse borrow(@Valid @RequestBody CreateBorrowRequest req) {
        String userId = SecurityUtils.currentUserId();
        return borrowService.borrowBook(userId, req);
    }

    @PostMapping("/returns/{borrowId}")
    public BorrowResponse returnBorrow(@PathVariable Long borrowId) {
        String userId = SecurityUtils.currentUserId();
        return borrowService.returnBook(userId, borrowId);
    }

    @GetMapping("/me")
    public List<BorrowResponse> myBorrows() {
        String userId = SecurityUtils.currentUserId();
        return borrowService.myBorrows(userId);
    }
}
