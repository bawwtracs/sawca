package sawca.support.im.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import sawca.support.im.entity.ChatGroup;
import sawca.support.im.entity.ChatGroupMember;
import sawca.support.im.entity.Message;
import sawca.support.im.repository.ChatGroupMemberRepository;
import sawca.support.im.repository.ChatGroupRepository;
import sawca.support.im.repository.MessageRepository;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/im")
public class IMRest {

    @Resource
    private MessageRepository messageRepository;
    @Resource
    private ChatGroupRepository chatGroupRepository;
    @Resource
    private ChatGroupMemberRepository chatGroupMemberRepository;

    @PostMapping("/message")
    public Message createMessage(@RequestBody Message request) {
        return messageRepository.save(request);
    }

    @GetMapping("/message")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/message/{id}")
    public Message getMessageById(@PathVariable("id") Long id) {
        return messageRepository.getOne(id);
    }

    @PutMapping("/message/{id}")
    public Message updateMessageById(@PathVariable("id") Long id, @RequestBody Message
            request) {
        request.setId(id);
        return messageRepository.save(request);
    }

    @PostMapping("/group")
    public ChatGroup createChatGroup(@RequestBody ChatGroup request) {
        return chatGroupRepository.save(request);
    }

    @GetMapping("/group")
    public List<ChatGroup> getAllChatGroups() {
        return chatGroupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    public ChatGroup getChatGroupById(@PathVariable("id") Long id) {
        return chatGroupRepository.getOne(id);
    }

    @PutMapping("/group/{id}")
    public ChatGroup updateChatGroupById(@PathVariable("id") Long id, @RequestBody ChatGroup
            request) {
        request.setId(id);
        return chatGroupRepository.save(request);
    }

    @PostMapping("/group/member")
    public ChatGroupMember createChatGroupMember(@RequestBody ChatGroupMember request) {
        return chatGroupMemberRepository.save(request);
    }

    @GetMapping("/group/member")
    public List<ChatGroupMember> getAllChatGroupMembers() {
        return chatGroupMemberRepository.findAll();
    }

    @GetMapping("/group/member/{id}")
    public ChatGroupMember getChatGroupMemberById(@PathVariable("id") Long id) {
        return chatGroupMemberRepository.getOne(id);
    }

    @PutMapping("/group/member/{id}")
    public ChatGroupMember updateChatGroupMemberById(@PathVariable("id") Long id, @RequestBody ChatGroupMember
            request) {
        request.setId(id);
        return chatGroupMemberRepository.save(request);
    }

}
