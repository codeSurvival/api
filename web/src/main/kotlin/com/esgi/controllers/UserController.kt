package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.accounts.queries.UserResume
import com.esgi.onebyone.application.accounts.login_user.ConnectedUser
import com.esgi.onebyone.application.accounts.queries.get_all_accounts.GetAllAccountsQuery
import com.esgi.onebyone.application.accounts.login_user.UserLoginCommand
import com.esgi.onebyone.application.accounts.queries.get_account_by_id.GetAccountByIdQuery
import com.esgi.onebyone.application.accounts.register_user.UserRegisterCommand
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*


@RequestMapping("user")
open class AccountsController constructor( private val mediator: Mediator) {


    @PostMapping("registration")
    open fun register(@RequestBody user: UserRegisterCommand): ResponseEntity<Unit> {

    }

}