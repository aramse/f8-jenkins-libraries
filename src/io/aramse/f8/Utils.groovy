package io.aramse.f8

class Utils {
  static def getCommitMsg() {
    return sh(script: 'git log -1 --pretty=%B', returnStdout: true).split('\n').first().trim()
  }

  static def getMergedBranch(){
    msg = get_commit_msg()
    return msg.startsWith('Merge pull request') ? msg.split('/').last() : null
  }

  static def getCommitter() {
    return slackUserIdFromEmail(sh(script: 'git --no-pager show -s --format=\'%ae\'', returnStdout: true).trim())
  }

  static def slackNotify(String result) {
    slackSend(color: result == 'SUCCESS' ? '#4ee44e' : '#ff0000', message: "${JOB_NAME} <${BUILD_URL}/display/redirect|Build #${BUILD_NUMBER}> " + result + " (last committer: <@" + getCommitter() + ">)")
  }
}
