package com.example.Rest_API_With_ArrayList;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/api/v1/posts")
@RestController
public class Controller {

    private int currentID = 4;

    List<Post> posts = new ArrayList<>();

    // add 4 posts to List
    public Controller(){
        posts.add(new Post(1, "Flask", "Web Development",
                "John", LocalDateTime.now(),
                new ArrayList<>(Arrays.asList("Framework", "Programming"))));
        posts.add(new Post(2, "Flask", "Web Development, System Applications",
                "John", LocalDateTime.now(),
                new ArrayList<>(Arrays.asList("Framework", "Programming", "Spring Boot", "ORM"))));
        posts.add(new Post(3, "AI", "Artificial Intelligence",
                "John", LocalDateTime.now(),
                new ArrayList<>(Arrays.asList("Technology", "Programming"))));
        posts.add(new Post(4, "Java", "Programming Language",
                "David", LocalDateTime.now(),
                new ArrayList<>(Arrays.asList("Technology", "Programming"))));
    }

    @Operation(summary = "Read all posts")
    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts( @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
        if (page <= 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        int start = (page - 1) * size;
        int end = Math.min(start + size, posts.size());

        if (start > posts.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Post> paginatedPosts = posts.subList(start, end);

        return ResponseEntity.ok(paginatedPosts);
    }

    @Operation(summary = "Read post by id")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id){
        for(Post post : posts){
            if(post.getId() == id){
                return ResponseEntity.ok(post);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Search post title")
    @GetMapping("/search")
    public ResponseEntity<List<Post>> getPostByTitle(@RequestParam String title){
        // create List to store if one or more posts is match with the title
        List<Post> temp = new ArrayList<>();
        for(Post post : posts){
            if(title.equals(post.getTitle()))
                temp.add(post);
        }
        if(temp.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(temp);
    }

    @Operation(summary = "Filter posts by author")
    @GetMapping("/author")
    public ResponseEntity<List<Post>> getPostByAuthor(@RequestParam String author){
        List<Post> temp = new ArrayList<>();
        for(Post post : posts){
            if(author.equals(post.getAuthor()))
                temp.add(post);
        }
        if(temp.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(temp);
    }

    @Operation(summary = "Delete post by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable int id){
        for (Post post : posts){
            if (post.getId() == id){
                posts.remove(post);
                return ResponseEntity.ok(post);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Update post by id")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable int id, @RequestBody Post post){
        for(Post p : posts){
            if(id == p.getId()){
                p.setTitle(post.getTitle());
                p.setContent(post.getContent());
                p.setAuthor(post.getAuthor());
                p.setTags(post.getTags());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Create new post")
    @PostMapping("")
    public ResponseEntity<Post> insertPost(@RequestBody Post post){
        Post postForInsert = new Post();

        postForInsert.setId(++currentID);
        postForInsert.setCreationDate(LocalDateTime.now());
        postForInsert.setAuthor(post.getAuthor());
        postForInsert.setTitle(post.getTitle());
        postForInsert.setContent(post.getContent());
        postForInsert.setTags(post.getTags());

        posts.add(postForInsert);
        return ResponseEntity.ok(postForInsert);
    }

    @Operation(summary = "Filter post by multiple tags")
    @GetMapping("/tags")
    public ResponseEntity<List<Post>> getPostsByMultipleTags(@RequestParam List<String> tags){
        List<Post> tagPosts = new ArrayList<>();
        Collections.sort(tags);   // sort List of String
        for (Post post : posts) {
            Collections.sort(post.getTags());  // sort List of String
            if (new ArrayList<>(post.getTags()).containsAll(tags))
                tagPosts.add(post);

        }
        if (tagPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(tagPosts);
        }
    }
}
