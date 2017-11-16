package fi.vm.yti.groupmanagement.security;

import fi.vm.yti.groupmanagement.model.OrganizationListItem;
import fi.vm.yti.security.AuthenticatedUserProvider;
import fi.vm.yti.security.YtiUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static fi.vm.yti.groupmanagement.util.CollectionUtil.filterToList;
import static fi.vm.yti.security.Role.ADMIN;

@Service
public class AuthorizationManager {

    private final AuthenticatedUserProvider userProvider;

    @Autowired
    AuthorizationManager(AuthenticatedUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public boolean canCreateOrganization() {
        return getUser().isSuperuser();
    }

    public boolean canEditOrganization(UUID organizationId) {
        return getUser().isSuperuser() || getUser().isInRole(ADMIN, organizationId);
    }

    public boolean canViewOrganization(UUID organizationId) {
        return this.canEditOrganization(organizationId);
    }

    public boolean canShowAuthenticationDetails() {
        return !getUser().isAnonymous();
    }

    public List<OrganizationListItem> filterViewableOrganizations(List<OrganizationListItem> organizations) {
        // XXX: might be necessary to implement at SQL-query level for optimal performance
        return filterToList(organizations, org -> canViewOrganization(org.getId()));
    }

    public boolean canBrowseUsers() {
        return getUser().isSuperuser() || getUser().isInRoleInAnyOrganization(ADMIN);
    }

    private @NotNull YtiUser getUser() {
        return userProvider.getUser();
    }
}