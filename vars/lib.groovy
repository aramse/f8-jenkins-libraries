def get_commit_msg() {
  return sh(script: 'git log -1 --pretty=%B', returnStdout: true).split('\n').first().trim()
}

def get_merged_branch(){
  msg = get_commit_msg()
  return msg.startsWith('Merge pull request') ? msg.split('/').last() : null
}

def getCommitter() {
  return slackUserIdFromEmail(sh(script: 'git --no-pager show -s --format=\'%ae\'', returnStdout: true).trim())
}

def slackNotify(String result) {
  slackSend(color: result == 'SUCCESS' ? '#4ee44e' : '#ff0000', message: "${JOB_NAME} <${BUILD_URL}/display/redirect|Build #${BUILD_NUMBER}> " + result + " (last committer: <@" + getCommitter() + ">)")
}
