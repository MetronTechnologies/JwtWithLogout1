package com.security.Controllers;



import com.security.DataTransferObjects.Requests.PostRequest;
import com.security.Models.PostModel;
import com.security.Services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;



    @PostMapping("/savePost")
    public ResponseEntity<Void> savePost(@RequestBody PostRequest postRequest){
        postService.savePost(postRequest);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostModel>> getAllPosts(){
        return status(OK).body(postService.allPosts());
    }

}
