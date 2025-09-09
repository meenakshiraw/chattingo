#!groovy
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","newpassword")
instance.setSecurityRealm(hudsonRealm)
instance.save()
println("Admin password reset complete. Username: admin, Password: newpassword")

