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
    public Message createMessage(@RequestBody Message message) {
        return messageRepository.save(message);
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
            message) {
        message.setId(id);
        return messageRepository.save(message);
    }

    @PostMapping("/group")
    public ChatGroup createChatGroup(@RequestBody ChatGroup group) {
        return chatGroupRepository.save(group);
    }

    @GetMapping("/groups")
    public List<ChatGroup> getAllChatGroups() {
        return chatGroupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    public ChatGroup getChatGroupById(@PathVariable("id") Long id) {
        return chatGroupRepository.getOne(id);
    }

    @PutMapping("/group/{id}")
    public ChatGroup updateChatGroupById(@PathVariable("id") Long id, @RequestBody ChatGroup
            group) {
        group.setId(id);
        return chatGroupRepository.save(group);
    }

    @PostMapping("/group/member")
    public ChatGroupMember createChatGroupMember(@RequestBody ChatGroupMember groupMember) {
        return chatGroupMemberRepository.save(groupMember);
    }

    @GetMapping("/group/members")
    public List<ChatGroupMember> getAllChatGroupMembers() {
        return chatGroupMemberRepository.findAll();
    }

    @GetMapping("/group/member/{id}")
    public ChatGroupMember getChatGroupMemberById(@PathVariable("id") Long id) {
        return chatGroupMemberRepository.getOne(id);
    }

    @PutMapping("/group/member/{id}")
    public ChatGroupMember updateChatGroupMemberById(@PathVariable("id") Long id, @RequestBody ChatGroupMember
            groupMember) {
        groupMember.setId(id);
        return chatGroupMemberRepository.save(groupMember);
    }

}
