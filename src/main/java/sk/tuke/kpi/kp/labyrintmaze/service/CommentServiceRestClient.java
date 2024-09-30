package sk.tuke.kpi.kp.labyrintmaze.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{
        private String url = "http://localhost:8080/api/comment";

        //@Autowired
        private RestTemplate restTemplate=new RestTemplate();

    @Override
    public Object addComment(Comment comment)  {
        restTemplate.postForEntity(url+"/"+comment.getGame(),comment,Comment.class);
        return null;
    }

    @Override
        public List<Comment> getComments(String game) {
            return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
        }

        @Override
        public void reset() {
            throw new UnsupportedOperationException("Reset is not supported on web interface.");
        }
}
