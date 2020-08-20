import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/filiere">
      <Translate contentKey="global.menu.entities.filiere" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/classe">
      <Translate contentKey="global.menu.entities.classe" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/etudiant">
      <Translate contentKey="global.menu.entities.etudiant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/absence">
      <Translate contentKey="global.menu.entities.absence" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/horaire">
      <Translate contentKey="global.menu.entities.horaire" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/matiere">
      <Translate contentKey="global.menu.entities.matiere" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/prof">
      <Translate contentKey="global.menu.entities.prof" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin">
      <Translate contentKey="global.menu.entities.admin" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
