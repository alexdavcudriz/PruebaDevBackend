variable "public_key" {}
variable "private_key" {}
variable "org_id" {}
variable "project_name" {
  default = "springboot-terraform-demo"
}
variable "cluster_name" {
  default = "Cluster0"
}
variable "db_user" {
  default = "user_demo"
}
variable "db_password" {
  default = "pass123abc"
}
