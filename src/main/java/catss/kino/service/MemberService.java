package catss.kino.service;


import catss.kino.dto.MemberRequest;
import catss.kino.dto.MemberResponse;
import catss.kino.entity.Member;
import catss.kino.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> getMembers(boolean includeAll, boolean includeReservations) {
        List<Member> repositoryList = memberRepository.findAll();
        List<MemberResponse> responseList = new ArrayList<>();
        for (Member member : repositoryList) {
            responseList.add(new MemberResponse(member, includeAll));
        }
        return responseList;
    }

    public MemberResponse addMember(MemberRequest body) {
        Member newMember = MemberRequest.getMemberEntity(body);
        if(memberRepository.existsById(body.getUsername())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"This user already exists");
        }
        newMember = memberRepository.save(newMember);
        return new MemberResponse(newMember, true);
    }

    public ResponseEntity<Boolean> editMember(MemberRequest body, String username) {
        Member member = getMemberByUsername(username);
        if(!body.getUsername().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot change username");
        }
        member.setPassword(body.getPassword());
        member.setEmail(body.getEmail());
        member.setFirstName(body.getFirstName());
        member.setLastName(body.getLastName());
        member.setStreet(body.getStreet());
        member.setCity(body.getCity());
        member.setZip(body.getZip());
        memberRepository.save(member);
        return ResponseEntity.ok(true);
    }


    public MemberResponse findById(String username) {
        Member member = getMemberByUsername(username);
        return new MemberResponse(member, true);
    }

    public void deleteMember(String username) {
        Member member = getMemberByUsername(username);
        memberRepository.delete(member);
    }

    public ResponseEntity<Boolean> setRanking(String username, int value) {
        Member member = getMemberByUsername(username);
        memberRepository.save(member);
        return ResponseEntity.ok(true);
    }

    public Member getMemberByUsername(String username){
        return memberRepository.findById(username).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Member with this username does not exist"));
    }

}
