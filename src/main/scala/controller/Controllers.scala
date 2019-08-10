package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    root.mount(ctx)
    companies.mount(ctx)
    members.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as(Symbol("index"))
  }

  object members extends _root_.controller.MembersController {
  }

  object companies extends _root_.controller.CompaniesController with Routes {

    // --------------
    // show

    val indexUrl = get("/companies")(showResources).as(Symbol("index"))
    val showUrl = get("/companies/:id") {
      params.getAs[Long]("id").map(id => showResource(id)).getOrElse(haltWithBody(404))
    }.as(Symbol("show"))

    // --------------
    // create

    val newUrl = get("/companies/new")(newResource).as(Symbol("new"))
    val createUrl = post("/companies")(createResource).as(Symbol("create"))

    // --------------
    // update

    val editUrl = get("/companies/:id/edit") {
      params.getAs[Long]("id").map(id => editResource(id)).getOrElse(haltWithBody(404))
    }.as(Symbol("edit"))
    val updateUrl = post("/companies/:id") {
      params.getAs[Long]("id").map(id => updateResource(id)).getOrElse(haltWithBody(404))
    }.as(Symbol("update"))

    // --------------
    // delete

    val destroyUrl = delete("/companies/:id") {
      params.getAs[Long]("id").map(id => destroyResource(id)).getOrElse(haltWithBody(404))
    }.as(Symbol("destroy"))

  }

}
