package com.security.Services;



import com.security.DataTransferObjects.Requests.PostRequest;
import com.security.Models.PostModel;
import com.security.Repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    public void savePost(PostRequest postRequest){
        if(postRequest != null){
            PostModel postmodel = PostModel
                    .builder()
                    .post(postRequest.getPost())
                    .postUrl(postRequest.getPostUrl())
                    .created(Instant.now())
                    .build();
            postRepo.save(postmodel);
        }
    }

    public List<PostModel> allPosts(){
        System.out.println(postRepo.findAll());
        return postRepo.findAll();
    }

}
