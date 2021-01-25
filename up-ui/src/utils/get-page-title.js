import defaultSettings from '@/settings'

const title = defaultSettings.title || 'UP-HABIT'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
