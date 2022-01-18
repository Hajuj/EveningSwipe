package com.example.user_demo_postgres.controller

import com.example.user_demo_postgres.AuthDto.*
import com.example.user_demo_postgres.dto.*
import com.example.user_demo_postgres.entity.TmdbMovieInfo
import com.example.user_demo_postgres.returnDto.GroupInfoDto
import com.example.user_demo_postgres.returnDto.RetMovieDetailsDto
import com.example.user_demo_postgres.returnDto.RetUserInfoDto
import com.example.user_demo_postgres.service.*
import com.example.user_demo_postgres.utils.mapper.MovieDetailMaper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.zalando.logbook.Logbook


@RestController
class MovieEndpoints (
    private val movieService: Basic_MovieService,
    private val tmdbService: TmdbService,
    private val groupService: GroupService,
    private val groupFilterService: GroupFilterService,
    private val movieRatingService: MovieRatingService,
    private val jwtService: JwtService,
    private val groupUserService: GroupUserService,
    private val userService: UserService,
    private val ratingUserService: RatingUserService,
    private val tmdbDetails: MovieDetailMaper
    ){




    /*@GetMapping("movie/{id}")
    fun getMovi(@PathVariable id: Int): ResponseEntity<MovieDTO> = ResponseEntity.ok(movieService.getMovie(id))*/

    var logbook = Logbook.create()
    @GetMapping("/api/movie/{id}")
    fun getBasicMovie(@PathVariable id: String): ResponseEntity<Basic_movieDTO> = ResponseEntity.ok(movieService.getBasicMovie(id))

    @GetMapping("/api/movie/group/)")
    fun getRandBasicMovie(): ResponseEntity<List<String>> =
        ResponseEntity.ok(movieService.getRandBasicMovies())

    @GetMapping("/api/movie/details")
    fun getMovieDetails(@RequestBody body: AuthMovieDetailsDto): ResponseEntity<RetMovieDetailsDto> {
        if (jwtService.validateToken(body.token.token)) {
            val tmdb = tmdbService.getMovieResource(body.movieId)

            tmdb?.let {
                val movieResuts = tmdb.movie_results
                return ResponseEntity.ok(tmdbDetails.parseDmtbDetails(tmdb.movie_results[0]))}

        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }

    @PostMapping("/api/group/create/")
    fun createGroup(@RequestBody groupDTO: AuthGroupDto) :ResponseEntity<String> {
        if (jwtService.validateToken(groupDTO.token.token)){
            groupDTO.group.owner = jwtService.getIdFromToken(groupDTO.token.token)
            val group = groupService.createGroup(groupDTO.group)
            groupUserService.adUserToGroup(groupDTO.group.owner, group.id)
            return ResponseEntity.ok("Group created")
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/api/filter/create")
    fun createFilter(@RequestBody dto: AuthFilterDto): ResponseEntity<GroupFilterDTO> {
        if (jwtService.validateToken(dto.token.token)) {
            val id = jwtService.getIdFromToken(dto.token.token)

            id?.let {
                if (id == groupService.getGroup(dto.filter.group_id).owner) {
                    val result = groupFilterService.createFilter(dto.filter)
                    val users = groupUserService.getUsersByGroup(dto.filter.group_id)
                    users?.map{
                        ratingUserService.createRatinUser(result.id, it)
                    }
                    return ResponseEntity.ok(result)
                }
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/api/user")
    fun getUser(@RequestBody dto: TokenDto): ResponseEntity<UserInfoDto> {
        if (jwtService.validateToken(dto.token)) {
            val id = jwtService.getIdFromToken(dto.token)

            id?.let {
                val groups = groupUserService.getGroupsByUser(id)
                val adminGroups = groupService.getGroupByAdmin(id)
                val user = userService.getUserById(id)

                user?.let{
                    val userInfo  = UserInfoDto(user.name, user.email, groups, adminGroups)
                    return ResponseEntity.ok(userInfo)
                    }

            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/api/group/add")
    fun addToGroup(@RequestBody dto: AuthGroupUserDto) :ResponseEntity<String> {
        if (jwtService.validateToken(dto.token.token)) {
            val id = jwtService.getIdFromToken(dto.token.token)

            id?.let {
                if (groupService.getOwner(dto.add.groupId) == id){
                     val user = userService.findByEmail(dto.add.toAdd)

                    user?.let{
                        groupUserService.adUserToGroup(user.id ,dto.add.groupId)
                        return ResponseEntity.ok("User added to Group")
                    }

                }

            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/api/filter/byid")
    fun getFilterByGroupid(@RequestBody body: AuthGetFilterDto) :ResponseEntity<List<GroupFilterDTO>>{
        if (jwtService.validateToken(body.token.token)) {
            val id = jwtService.getIdFromToken(body.token.token)

            id?.let {
                println("token valid")
                if (groupUserService.isUserInGroup(body.groupId, id)) {
                    return ResponseEntity.ok(groupFilterService.getFilterByGroupId(body.groupId))
                }
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }

    @GetMapping("/api/filter/movies/{id}")
    fun getMoviesByFilterId(@PathVariable id :Int): ResponseEntity<List<String>>{
        return ResponseEntity.ok(groupFilterService.getMoviesByGroupId(id))

    }

    @PostMapping("/api/filter/rate")
    fun rateMovie(@RequestBody body: AuthRateMovieDto) : ResponseEntity<String>{
        if (jwtService.validateToken(body.token.token)) {
            val id = jwtService.getIdFromToken(body.token.token)

            id?.let {
                val groupId = groupFilterService.getGroupIdByFilter(body.rating.filterId)
                if (groupUserService.isUserInGroup(groupId, id)) {
                    movieRatingService.rateMovie(body.rating.movieId, body.rating.filterId)
                    ratingUserService.rateMovieInFilter(body.rating.filterId, id)
                    return ResponseEntity.ok("Rating added to movie in filter")

                }
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }

    @GetMapping("/api/filter/rating")
    fun getMovieRating(@RequestBody body:AuthGetFilterRatingDto): ResponseEntity<List<MovieRatingDTO>>{
        if (jwtService.validateToken(body.token.token)) {
        val id = jwtService.getIdFromToken(body.token.token)

        id?.let {
            val groupId = groupFilterService.getGroupIdByFilter(body.filterId)
            if (groupUserService.isUserInGroup(groupId, id)) {
                return ResponseEntity.ok(movieRatingService.getRating(body.filterId))

            }
        }
    }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }

    @GetMapping("/api/group/info")
    fun getGroupInformation(@RequestBody body:AuthGroupInfoDto): ResponseEntity<GroupInfoDto>{
        if (jwtService.validateToken(body.token.token)) {
            val id = jwtService.getIdFromToken(body.token.token)

            id?.let {
                if (groupUserService.isUserInGroup(body.groupId, id)){
                    val users = groupUserService.getUsersByGroup(body.groupId)
                    val filter = groupFilterService.getFilterByGroupId(body.groupId)
                    val filterIds: List<Int> = filter.map { it.id }
                    val group = groupService.getGroup(body.groupId)
                    val groupInfo = GroupInfoDto(users, filterIds, group.name)
                    return ResponseEntity.ok(groupInfo)
                }
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }

    @GetMapping("/api/user/info")
    fun getUserInfo(@RequestBody body:AuthUserInfoDto): ResponseEntity<RetUserInfoDto>{
        if (jwtService.validateToken(body.token.token)) {
            val id = jwtService.getIdFromToken(body.token.token)

            id?.let {
                val user  = userService.getUserById(body.userId)
                user?.let{
                    return ResponseEntity.ok(RetUserInfoDto(user.name))
                }


                }
            }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

    }
}