package com.dogadopt.dog_adopt.service.comment;

import com.dogadopt.dog_adopt.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
}
