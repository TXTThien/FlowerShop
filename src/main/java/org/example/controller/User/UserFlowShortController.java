package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.controller.FlowShortController;
import org.example.dto.PostVideo;
import org.example.dto.VideoCommentDTO;
import org.example.dto.VideoDTO;
import org.example.entity.*;
import org.example.entity.enums.Commentable;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.VideoCommentRepository;
import org.example.repository.VideoImageRepository;
import org.example.repository.VideoInteractRepository;
import org.example.repository.VideoRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/flowshort")
@RequiredArgsConstructor
public class UserFlowShortController {
    private final IVideoService videoService;
    private final IVideoCommentService videoCommentService;
    private final IVideoImageService videoImageService;
    private final IVideoInteractService videoInteractService;
    private final VideoRepository videoRepository;
    private final VideoCommentRepository videoCommentRepository;
    private final VideoImageRepository videoImageRepository;
    private final VideoInteractRepository videoInteractRepository;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final FlowShortController flowShortController;

    @GetMapping("/video/{id}/getComment")
    private ResponseEntity<?> getComment (@PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        List<VideoCommentDTO> videoCommentDTO = flowShortController.getVideoCommentDTOS(id,accountid);
        return ResponseEntity.ok(videoCommentDTO);
    }

    @RequestMapping("video/{id}/like")
    private ResponseEntity<?> likeVideo(@PathVariable int id)
    {
        int accountId = getIDAccountFromAuthService.common();
        List<VideoInteract> hadLikes = videoInteractService.findLikeVideoYet(id,accountId);
        Video video = videoService.findVideoByIDEnable(id);
        if (hadLikes != null && !hadLikes.isEmpty())
        {
            video.setLikes(video.getLikes()-1);
            videoRepository.save(video);
            videoInteractRepository.deleteAll(hadLikes);
            return ResponseEntity.ok("Success");
        }
        VideoInteract videoInteract = new VideoInteract();
        videoInteract.setVideo(videoService.findVideoByIDEnable(id));
        videoInteract.setAccountID(accountService.getAccountById(accountId));
        video.setLikes(video.getLikes()+1);
        videoRepository.save(video);
        videoInteractRepository.save(videoInteract);
        return ResponseEntity.ok("Success");
    }
    @RequestMapping("video/{id}/comment")
    private ResponseEntity<?> commentVideo(@PathVariable int id, @RequestBody String text)
    {
        Video video = videoService.findVideoByIDEnable(id);
        if (video.getCommentable() == Commentable.NO)
        {
            return ResponseEntity.badRequest().body("Fail");
        }
        int accountId = getIDAccountFromAuthService.common();
        VideoComment videoComment = new VideoComment();
        videoComment.setVideo(video);
        videoComment.setComment(text);
        videoComment.setAccountID(accountService.getAccountById(accountId));
        videoComment.setDateTime(LocalDateTime.now());
        videoComment.setStatus(Status.ENABLE);
        videoComment.setLike(0L);
        video.setComments(video.getComments()+1);
        videoRepository.save(video);
        videoCommentRepository.save(videoComment);
        return ResponseEntity.ok("Success");
    }

    @RequestMapping("comment/{id}/like")
    private ResponseEntity<?> likeComment(@PathVariable int id)
    {
        int accountId = getIDAccountFromAuthService.common();
        VideoComment videoComment = videoCommentService.findVidCommentByIDEnable(id);
        List<VideoInteract> hadLikes = videoInteractService.findLikeCommentYet(id,accountId);
        if (hadLikes != null && !hadLikes.isEmpty())
        {
            videoComment.setLike(videoComment.getLike()-1);
            videoCommentRepository.save(videoComment);
            videoInteractRepository.deleteAll(hadLikes);
            return ResponseEntity.ok("Success");
        }
        VideoInteract videoInteract = new VideoInteract();
        videoInteract.setVideoComment(videoCommentService.findVidCommentByIDEnable(id));
        videoInteract.setAccountID(accountService.getAccountById(accountId));
        videoComment.setLike(videoComment.getLike()+1);
        videoCommentRepository.save(videoComment);
        videoInteractRepository.save(videoInteract);
        return ResponseEntity.ok("Success");
    }
    @RequestMapping("comment/{id}/comment")
    private ResponseEntity<?> commentVideoComment(@PathVariable int id, @RequestBody String text)
    {
        VideoComment comment = videoCommentService.findVidCommentByIDEnable(id);
        Video video = null;
        VideoComment fatherComment = comment.getFatherComment();
        if (fatherComment != null) {
            video = fatherComment.getVideo();
        }
        if (video == null) {
            video = comment.getVideo();
        }
        if (video.getCommentable() == Commentable.NO)
        {
            return ResponseEntity.badRequest().body("Fail");
        }
        int accountId = getIDAccountFromAuthService.common();
        VideoComment videoComment = new VideoComment();
        if (comment.getFatherComment() == null)
        {
            videoComment.setFatherComment(comment);
        }
        else
        {
            videoComment.setFatherComment(comment.getFatherComment());
        }
        videoComment.setComment(text);
        videoComment.setAccountID(accountService.getAccountById(accountId));
        videoComment.setDateTime(LocalDateTime.now());
        videoComment.setStatus(Status.ENABLE);
        videoComment.setLike(0L);
        video.setComments(video.getComments()+1);
        videoRepository.save(video);
        videoCommentRepository.save(videoComment);
        return ResponseEntity.ok("Success");
    }

    @RequestMapping("comment/{id}/delete")
    private ResponseEntity<?> deleteComment(@PathVariable int id)
    {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);
        VideoComment videoComment = videoCommentService.findVidCommentByIDEnable(id);
        Video video = videoComment.getVideo();
        if (video == null)
        {
            video = videoComment.getFatherComment().getVideo();
        }
        if (videoComment.getAccountID().equals(account) || Role.admin.equals(account.getRole()) || Role.staff.equals(account.getRole()))
        {
            if (videoComment.getVideo() == null)
            {
                video.setComments(video.getComments()-1);
                videoComment.setStatus(Status.DISABLE);
                videoRepository.save(video);
                videoCommentRepository.save(videoComment);
                return ResponseEntity.ok("Success");
            }
            else if (videoComment.getFatherComment() == null)
            {
                videoComment.setStatus(Status.DISABLE);

                List<VideoComment> videoComments = videoCommentService.findCommentChild(videoComment.getId());
                for (VideoComment childComment : videoComments) {
                    childComment.setStatus(Status.DISABLE);
                }

                video.setComments(video.getComments() - videoComments.size() - 1);

                videoRepository.save(video);
                videoCommentRepository.save(videoComment);  // Save main comment
                videoCommentRepository.saveAll(videoComments);  // Save all child comments

                return ResponseEntity.ok("Success");

            }
        }
        return ResponseEntity.badRequest().body("Fail");
    }
    @RequestMapping("video/{id}/mute")
    private ResponseEntity<?> muteComment(@PathVariable int id)
    {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);
        Video video = videoService.findVideoByIDEnable(id);
        if (video.getAccountID().equals(account) || account.getRole().equals(Role.staff) || account.getRole().equals(Role.admin))
        {
            if (video.getCommentable()==Commentable.YES)
            {
                video.setCommentable(Commentable.NO);
            }
            else
            {
                video.setCommentable(Commentable.YES);
            }
            videoRepository.save(video);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("Fail");

    }
    @GetMapping("/getall")
    private ResponseEntity<?> getAllVideo ()
    {
        int accountid = getIDAccountFromAuthService.common();
        List<Video> videos = videoService.findVideoByAccountIDEnable(accountid);
        Map<String, Object> response = new HashMap<>();
        response.put("videos", videos);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getall/{id}/next")
    private ResponseEntity<?> getNextVideo (@PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        List<Video> videos = videoService.findVideoByAccountIDEnable(accountid);
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId() == id && i + 1 < videos.size()) {
                Video video = videos.get(i+1);
                VideoDTO videoDTO = flowShortController.getVideoDTO(video.getId());
                return ResponseEntity.ok(videoDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
    }

    @PostMapping("")
    private ResponseEntity<?> postVideo (@RequestBody PostVideo postVideo)
    {
        int accountid = getIDAccountFromAuthService.common();
        Video video = new Video();
        video.setAccountID(accountService.getAccountById(accountid));
        video.setDate(LocalDateTime.now());
        video.setStatus(Status.ENABLE);
        video.setComments(0L);
        video.setDescription(postVideo.getDescription());
        video.setCommentable(postVideo.getCommentable());
        video.setLikes(0L);
        video.setTitle(postVideo.getTitle());
        video.setViews(0L);
        video.setThumb_url(postVideo.getThumb_url());
        video.setVid_url(postVideo.getVid_url());
        videoRepository.save(video);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateVideo (@RequestBody PostVideo postVideo, @PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        Video video = videoService.findVideoByIDEnable(id);
        if (video.getAccountID().getAccountID() != accountid)
        {
            return ResponseEntity.badRequest().body("Fail");
        }
        video.setDescription(postVideo.getDescription());
        video.setCommentable(postVideo.getCommentable());
        video.setTitle(postVideo.getTitle());
        video.setThumb_url(postVideo.getThumb_url());
        video.setVid_url(postVideo.getVid_url());
        videoRepository.save(video);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteVideo (@PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        Video video = videoService.findVideoByIDEnable(id);
        Account account = accountService.getAccountById(accountid);
        if (video.getAccountID().getAccountID() == accountid || Role.admin.equals(account.getRole()) || Role.staff.equals(account.getRole()) )
        {
            video.setStatus(Status.DISABLE);
            videoRepository.save(video);
            return ResponseEntity.ok("Success");
        }
        else
        {
            return ResponseEntity.badRequest().body("Fail");
        }
    }
}
