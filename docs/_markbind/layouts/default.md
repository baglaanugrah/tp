<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">TeamEventPro</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UG.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2526S2-CS2103T-W11-1/tp" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UG.html) :expanded:
  * [Introduction and App Modes]({{ baseUrl }}/UG.html)
    * [About TeamEventPro]({{ baseUrl }}/UG.html#1-about-teameventpro)
    * [Understanding App Modes]({{ baseUrl }}/UG.html#2-understanding-app-modes)
  * [Getting Started]({{ baseUrl }}/UserGuideGettingStarted.html)
    * [Prerequisites]({{ baseUrl }}/UserGuideGettingStarted.html#1-prerequisites)
    * [Install and launch]({{ baseUrl }}/UserGuideGettingStarted.html#2-install-and-launch)
    * [First-time setup]({{ baseUrl }}/UserGuideGettingStarted.html#3-first-time-setup)
    * [First 5-minute workflow]({{ baseUrl }}/UserGuideGettingStarted.html#4-first-5-minute-workflow)
    * [Where to go next]({{ baseUrl }}/UserGuideGettingStarted.html#5-where-to-go-next)
  * [Command Fundamentals]({{ baseUrl }}/UserGuideCommandFundamentals.html)
    * [Command notation]({{ baseUrl }}/UserGuideCommandFundamentals.html#1-command-notation)
    * [Command structure and modes]({{ baseUrl }}/UserGuideCommandFundamentals.html#2-command-structure-and-modes)
    * [Prefix reference]({{ baseUrl }}/UserGuideCommandFundamentals.html#3-prefix-reference)
    * [Index and list behavior]({{ baseUrl }}/UserGuideCommandFundamentals.html#4-index-and-list-behavior)
    * [Common mistakes and quick fixes]({{ baseUrl }}/UserGuideCommandFundamentals.html#5-common-mistakes-and-quick-fixes)
  * [Event Commands]({{ baseUrl }}/UserGuideEvents.html)
    * [Event Creation and Setup]({{ baseUrl }}/UserGuideEvents.html#1-event-creation-and-setup)
    * [Event Maintenance]({{ baseUrl }}/UserGuideEvents.html#2-event-maintenance)
    * [Event Navigation]({{ baseUrl }}/UserGuideEvents.html#3-event-navigation)
    * [Application Exit]({{ baseUrl }}/UserGuideEvents.html#4-application-exit)
  * [Participant Commands]({{ baseUrl }}/UserGuideParticipants.html)
    * [Participant Management]({{ baseUrl }}/UserGuideParticipants.html#1-participant-management)
    * [Team and Attendance Management]({{ baseUrl }}/UserGuideParticipants.html#2-team-and-attendance-management)
    * [Search, Filtering, and Viewing]({{ baseUrl }}/UserGuideParticipants.html#3-search-filtering-and-viewing)
    * [Import and Export]({{ baseUrl }}/UserGuideParticipants.html#4-import-and-export)
    * [Event Navigation]({{ baseUrl }}/UserGuideParticipants.html#5-event-navigation)
  * [Common Commands]({{ baseUrl }}/UserGuideCommonCommands.html)
    * [Commands Available in Both Modes]({{ baseUrl }}/UserGuideCommonCommands.html#1-commands-available-in-both-modes)
    * [Help command]({{ baseUrl }}/UserGuideCommonCommands.html#2-help-command)
    * [List command]({{ baseUrl }}/UserGuideCommonCommands.html#3-list-command)
    * [Search command]({{ baseUrl }}/UserGuideCommonCommands.html#4-search-command)
    * [Switch Mode command]({{ baseUrl }}/UserGuideCommonCommands.html#5-switch-mode-command)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
