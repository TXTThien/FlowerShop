package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BlogCommentDTO;
import org.example.dto.VideoCommentDTO;
import org.example.dto.VideoDTO;
import org.example.entity.*;
import org.example.repository.VideoRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/flowshort")
@RequiredArgsConstructor
public class FlowShortController {
    private final IVideoService videoService;
    private final IVideoCommentService videoCommentService;
    private final IVideoImageService videoImageService;
    private final IVideoInteractService videoInteractService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final VideoRepository videoRepository;
    private final IAccountService accountService;
    @GetMapping("")
    private ResponseEntity<?> findAll ()
    {
        List<Video> videos= videoService.findAllEnable();
        Map<String, Object> response = new HashMap<>();
        response.put("videos", videos);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/account/{id}")
    private ResponseEntity<?> getAllFlowShortAccount (@PathVariable int id)
    {
        List<Video> videos = videoService.findVideoByAccountIDEnable(id);
        Map<String, Object> response = new HashMap<>();
        response.put("videos", videos);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/account/{accountid}/video/{videoid}/next")
    private ResponseEntity<?> getNextFlowShortAccount (@PathVariable int accountid, @PathVariable int videoid)
    {
        List<Video> videos = videoService.findVideoByAccountIDEnable(accountid);
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId() == videoid) {
                // Kiểm tra nếu có video trước
                if (i + 1 < videos.size()) {
                    Video prevVideo = videos.get(i + 1);
                    VideoDTO videoDTO = getVideoDTO(prevVideo.getId());
                    return ResponseEntity.ok(videoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No previous video available.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
    }
    @GetMapping("/account/{accountid}/video/{videoid}/prev")
    private ResponseEntity<?> getPrevFlowShortAccount (@PathVariable int accountid, @PathVariable int videoid)
    {
        List<Video> videos = videoService.findVideoByAccountIDEnable(accountid);
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId() == videoid) {
                // Kiểm tra nếu có video trước
                if (i - 1 >= 0) {
                    Video prevVideo = videos.get(i - 1);
                    VideoDTO videoDTO = getVideoDTO(prevVideo.getId());
                    return ResponseEntity.ok(videoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No previous video available.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
    }
    @GetMapping("/{id}")
    private ResponseEntity<?> getFlowShort (@PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();

        VideoDTO videoDTO = getVideoDTO(id);
        Map<String, Object> response = new HashMap<>();
        if (accountid != -1)
        {
            Account account = accountService.getAccountById(accountid);
            response.put("account", account);
        }
        response.put("videoDTO", videoDTO);
        return ResponseEntity.ok(response);
    }
    @RequestMapping("/{id}/plusview")
    private void plusView (@PathVariable int id)
    {
        Video video = videoService.findVideoByIDEnable(id);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }
    public VideoDTO getVideoDTO (int id)
    {
        Video video = videoService.findVideoByIDEnable(id);
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setVideo(video);
        int accountid = getIDAccountFromAuthService.common();
       System.out.println("VideoID: "+id+" AccountID: "+accountid);

        List<VideoCommentDTO> videoCommentDTOS = getVideoCommentDTOS(id,accountid);
        videoDTO.setVideoCommentDTOS(videoCommentDTOS);
        List<VideoInteract> videoInteract = videoInteractService.findLikeVideoYet(id, accountid);
        System.out.println("videoInteract: "+videoInteract);
        videoDTO.setLiked(videoInteract != null && !videoInteract.isEmpty());

        return videoDTO;
    }
    public List<VideoCommentDTO> getVideoCommentDTOS (int id, int accountid)
    {
        List<VideoCommentDTO> videoCommentDTOS = new ArrayList<>();
        List<VideoComment> videoComments = videoCommentService.findCommentByVidIDAndStatus(id);

        for (VideoComment videoComment : videoComments) {
            VideoCommentDTO videoCommentDTO = new VideoCommentDTO();
            List<VideoImage> videoImages = videoImageService.findImageByCommentID(videoComment.getId());
            List<VideoComment> commentChildren = Optional.ofNullable(videoCommentService.findCommentChild(videoComment.getId())).orElse(Collections.emptyList());
            List<VideoCommentDTO> childCommentsDTO = new ArrayList<>();
            List<VideoInteract> commentInteracts = videoInteractService.findLikeCommentYet(videoComment.getId(), accountid);

            // Xử lý comment child
            for (VideoComment child : commentChildren) {
                VideoCommentDTO childCommentDTO = new VideoCommentDTO();
                List<VideoInteract> childCommentInteracts = videoInteractService.findLikeCommentYet(child.getId(), accountid);
                List<VideoImage> imageChildComment = videoImageService.findImageByCommentID(child.getId());

                childCommentDTO.setVideoComment(child);
                childCommentDTO.setVideoImages(imageChildComment);
                childCommentDTO.setLikeComment(childCommentInteracts != null && !childCommentInteracts.isEmpty());
                childCommentsDTO.add(childCommentDTO);
            }

            videoCommentDTO.setVideoComment(videoComment);
            videoCommentDTO.setVideoImages(videoImages);
            videoCommentDTO.setNumberCommentChild(commentChildren.size()); // Gán số lượng comment con
            videoCommentDTO.setChildComment(childCommentsDTO);
            videoCommentDTO.setLikeComment(commentInteracts != null && !commentInteracts.isEmpty());
            videoCommentDTOS.add(videoCommentDTO); // Thêm comment vào danh sách
        }
        Collections.reverse(videoCommentDTOS);
        return videoCommentDTOS;
    }

    @GetMapping("/{id}/next")
    private ResponseEntity<?> getNextFlowShort(@PathVariable int id) {
        List<Video> videos = videoService.findAllEnable();
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId() == id) {
                if (i + 1 < videos.size()) {
                    Video nextVideo = videos.get(i + 1);
                    VideoDTO videoDTO = getVideoDTO(nextVideo.getId());
                    return ResponseEntity.ok(videoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No next video available.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
    }
    @GetMapping("/{id}/prev")
    private ResponseEntity<?> getPrevFlowShort(@PathVariable int id) {
        List<Video> videos = videoService.findAllEnable();
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId() == id) {
                // Kiểm tra nếu có video trước
                if (i - 1 >= 0) {
                    Video prevVideo = videos.get(i - 1);
                    VideoDTO videoDTO = getVideoDTO(prevVideo.getId());
                    return ResponseEntity.ok(videoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No previous video available.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
    }

}
