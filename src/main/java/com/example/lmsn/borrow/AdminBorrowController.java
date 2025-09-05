package com.example.lmsn.borrow;

import com.example.lmsn.borrow.dto.BorrowResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/borrows")
public class AdminBorrowController {

    private final BorrowRepository borrowRepo;

    public AdminBorrowController(BorrowRepository borrowRepo) {
        this.borrowRepo = borrowRepo;
    }

    @GetMapping
    public List<BorrowResponse> listAll() {
        return borrowRepo.findAll().stream().map(r -> new BorrowResponse(
                r.getId(),
                r.getBook().getId(),
                r.getBook().getTitle(),
                r.getUserId(),
                r.getBorrowedAt(),
                r.getDueAt(),
                r.getReturnedAt(),
                r.getStatus().name(),
                r.getRenewalCount()
        )).toList();
    }
}
