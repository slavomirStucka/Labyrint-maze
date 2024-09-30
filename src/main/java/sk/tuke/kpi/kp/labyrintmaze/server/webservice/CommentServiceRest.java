package sk.tuke.kpi.kp.labyrintmaze.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;
    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game){
        return commentService.getComments(game);
    }
    @PostMapping("/{game}")
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

}
