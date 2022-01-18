package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.entity.GroupUsers
import com.example.user_demo_postgres.repository.GroupUserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GroupUserService(
    private val groupUserRepository: GroupUserRepository
) {

    fun adUserToGroup(userid:Int, groupId:Int){
        groupUserRepository.save(GroupUsers(0, groupId = groupId, userId = userid))

    }
    fun isUserInGroup(grupId: Int , userId : Int): Boolean{
        val groupUser = groupUserRepository.getGroupUserById(grupId)
        groupUser?.let {
            for (user in it){
                if (user.userId == userId)
                return true
            }

        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    fun getUsersByGroup(groupId: Int): List<Int>{
        val users = groupUserRepository.getGroupUserById(groupId)
        return users.map{it.userId}



    }
    fun getGroupsByUser(userId: Int): List<Int> = groupUserRepository.getGroupsByUser(userId)

}