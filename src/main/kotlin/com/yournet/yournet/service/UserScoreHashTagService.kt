package com.yournet.yournet.service

import com.yournet.yournet.model.entity.HashTag
import com.yournet.yournet.model.entity.User
import com.yournet.yournet.model.entity.UserScoreHashTag
import com.yournet.yournet.repository.HashTagRepository
import com.yournet.yournet.repository.UserScoreHashTagRepository
import org.springframework.stereotype.Service

@Service
class UserScoreHashTagService(
    private val userService: UserService,
    private val userScoreHashTagRepository: UserScoreHashTagRepository,
    private val hashTagRepository: HashTagRepository
) {
    fun updateScore(jwt: String, hashTagName: String) {
        val findUser = userService.getValidUser(jwt)
        val findTag = hashTagRepository.findByHashTagName(hashTagName)
        val findScore: UserScoreHashTag?
        //해당 사용자가 hashtag를 쓴 적 없으면 UserScoreHashTag 생성후 5점으로 초기화
        if (findUser != null && findTag != null) {
            findScore = getValidScore(findUser, findTag)
            //Score 정보가 없으면 새로 생성
            if (findScore == null) {
                val newScore = UserScoreHashTag(
                    user = findUser,
                    hashTag = findTag,
                    score = 5,
                    count = 1
                )
                userScoreHashTagRepository.save(newScore)
            } else {
                findScore.score += 7
                findScore.count += 1
                //update하여 저장
                userScoreHashTagRepository.save(findScore)
            }
        }
        // TODO: 해당 사용자가 3회 이상 hashtag를 쓴 적 있으면 7점씩 증가
        //TODO: 많이 쓰면 쓸수록 더 점수가 많이 올라가도록

    }

    fun getValidScore(user: User, tag: HashTag): UserScoreHashTag? {
        return userScoreHashTagRepository.findByUserAndHashTag(user, tag)
    }
}